package gui;

import Controller.Controller;
import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.iHashMap;
import Model.DataStructures.iHeap;
import Model.ProgramState.ProgramState;
import Model.Statements.iStatement;
import Model.Values.iValue;
import View.Interpreter;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

class Pair<T1, T2> {
    T1 first;
    T2 second;

    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }
}

public class ProgramDetailsController {

    private Controller __controller;

    @FXML
    public TextField numberOfStatesText;
    @FXML
    public TableColumn<Pair<String, iValue>, String> variableNameCol;
    @FXML
    public TableColumn<Pair<String, iValue>, String> variableValueCol;
    @FXML
    public TableColumn<Pair<Integer, iValue>, Integer> heapAddressCol;
    @FXML
    public TableColumn<Pair<Integer, iValue>, String> heapValueCol;
    @FXML
    public ListView<Integer> programStatesList;
    @FXML
    public TableView<Pair<String, iValue>> variablesTable;
    @FXML
    public ListView<String> executionStackList;
    @FXML
    public Button runProgramBtn;
    @FXML
    public ListView<String> fileTable;
    @FXML
    public ListView<String> outputList;
    @FXML
    public TableView<Pair<Integer, iValue>> heapTable;



    private void populateLists() throws DataStructureException {
        populateHeapTableView();
        populateOutputListView();
        populateFileTableListView();
        populateProgramStateIdentifiersListView();
        populateSymbolTableView();
        populateExecutionStackListView();
    }

    @FXML
    public void initialize() {
        programStatesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        heapAddressCol.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().first).asObject());
        heapValueCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
        variableNameCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().first));
        variableValueCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().second.toString()));
    }
    public void runSequentially(MouseEvent mouseEvent) {
        if (__controller != null) {
            try {
                List<ProgramState> programStates = Objects.requireNonNull(__controller.getStates());
                if (programStates.size() > 0) {
                    __controller.runProgramSequentially();
                    populateLists();
                    programStates = __controller.removeCompletedStates(__controller.getStates());
                    __controller.setStates(programStates);
                    populateProgramStateIdentifiersListView();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("An error has occured!");
                    alert.setContentText("There is nothing left to execute!");
                    alert.showAndWait();
                }
            } catch (DataStructureException | StatementException | ExpressionException | IOException | InterruptedException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Execution error!");
                alert.setHeaderText("An execution error has occured!");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("An error has occured!");
            alert.setContentText("No program selected!");
            alert.showAndWait();
        }
    }

    public void setController(Controller controller) throws DataStructureException {
        __controller = controller;
        populateLists();
    }

    private ProgramState getCurrentProgramState() throws DataStructureException {
        if (__controller.getStates().size() == 0)
            return null;
        else {
            int currentId = programStatesList.getSelectionModel().getSelectedIndex();
            if (currentId == -1)
                return __controller.getStates().get(0);
            else
                return __controller.getStates().get(currentId);
        }
    }

    @FXML
    private void changeProgramState(MouseEvent event) throws DataStructureException {
        try {
            populateExecutionStackListView();
            populateSymbolTableView();
        }
        catch (DataStructureException e) {
        }
    }

    private void populateNumberOfProgramStatesTextField() throws DataStructureException {
        List<ProgramState> programStates = __controller.getStates();
        numberOfStatesText.setText(String.valueOf("Number of Program states: " + programStates.size()));
    }

    private void populateHeapTableView() throws DataStructureException {
        ProgramState programState = getCurrentProgramState();
        iHeap heap = Objects.requireNonNull(programState).getHeap();
        ArrayList<Pair<Integer, iValue>> heapEntries = new ArrayList<>();
        for(Map.Entry<Integer, iValue> entry: heap.getData().entrySet()) {
            heapEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        heapTable.setItems(FXCollections.observableArrayList(heapEntries));
    }

    private void populateOutputListView() throws DataStructureException {
        ProgramState programState = getCurrentProgramState();
        List<String> output = new ArrayList<>();
        List<iValue> outList = Objects.requireNonNull(programState).getList().get();
        for (int index = 0; index < outList.size(); index++){
            output.add(outList.get(index).toString());
        }
        outputList.setItems(FXCollections.observableArrayList(output));
    }

    private void populateFileTableListView() throws DataStructureException {
        ProgramState programState = getCurrentProgramState();
        List<String> files = new ArrayList<>(Objects.requireNonNull(programState).getFilesHashmap().getData().keySet());
        fileTable.setItems(FXCollections.observableList(files));
    }

    private void populateProgramStateIdentifiersListView() throws DataStructureException {
        List<ProgramState> programStates = __controller.getStates();
        List<Integer> idList = programStates.stream().map(ProgramState::getStateID).collect(Collectors.toList());
        programStatesList.setItems(FXCollections.observableList(idList));
        populateNumberOfProgramStatesTextField();
    }

    private void populateSymbolTableView() throws DataStructureException {
        ProgramState programState = getCurrentProgramState();
        iHashMap<String, iValue> symbolTable = Objects.requireNonNull(programState).getHashmap();
        ArrayList<Pair<String, iValue>> symbolTableEntries = new ArrayList<>();
        for (Map.Entry<String, iValue> entry: symbolTable.getData().entrySet()) {
            symbolTableEntries.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        variablesTable.setItems(FXCollections.observableArrayList(symbolTableEntries));
    }

    private void populateExecutionStackListView() throws DataStructureException {
        ProgramState programState = getCurrentProgramState();
        List<String> executionStackToString = new ArrayList<>();
        if (programState != null)
            for (iStatement statement: programState.getStack().getList()) {
                executionStackToString.add(statement.toString());
            }
        executionStackList.setItems(FXCollections.observableList(executionStackToString));
    }
}
