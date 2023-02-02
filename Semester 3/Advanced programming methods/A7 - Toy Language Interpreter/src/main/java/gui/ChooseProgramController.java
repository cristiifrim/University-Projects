package gui;

import Controller.Controller;
import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.szHashMap;
import Model.DataStructures.szHeap;
import Model.DataStructures.szList;
import Model.DataStructures.szStack;
import Model.Expressions.*;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.iRepository;
import Repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChooseProgramController {

    @FXML
    private ProgramDetailsController __detailsService;
    @FXML
    public Button selectProgramBtn;
    @FXML
    public ListView<iStatement> programList;

    public void setDetailsService(ProgramDetailsController detailsService) {
        __detailsService = detailsService;
    }
    @FXML
    public void initialize() {
        programList.setItems(getAllStatements());
        programList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private ObservableList<iStatement> getAllStatements() {
        List<iStatement> prgList = new ArrayList<>();

        iStatement ex1= new CompoundStatement(new DeclarationStatement("v",new IntType()),
                new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableEvaluation("v"))));

        iStatement ex2 = new CompoundStatement(new DeclarationStatement("a",new IntType()),
                new CompoundStatement(new DeclarationStatement("b",new IntType()),
                        new CompoundStatement(new AssignmentStatement("a",
                                new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                                        new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), '*'), '+')),
                                new CompoundStatement(new AssignmentStatement("b",
                                        new ArithmeticExpression(new VariableEvaluation("a"), new ValueExpression(new IntValue(1)), '+')),
                                        new PrintStatement(new VariableEvaluation("b"))))));

        iStatement ex3 = new CompoundStatement(new DeclarationStatement("a",new BoolType()),
                new CompoundStatement(new DeclarationStatement("v", new IntType()),
                        new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfElseStatement(new VariableEvaluation("a"),
                                        new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                                        new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                                        new PrintStatement(new VariableEvaluation("v"))))));

        iStatement ex4 = new CompoundStatement(new DeclarationStatement("varf", new StringType()),
                new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenReadFileStatement(new VariableEvaluation("varf")),
                                new CompoundStatement(new DeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(new ReadFileStatement(new VariableEvaluation("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableEvaluation("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableEvaluation("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableEvaluation("varc")),
                                                                        new CloseReadFileStatement(new VariableEvaluation("varf"))))))))));


        iStatement ex5 = new CompoundStatement(new DeclarationStatement("a", new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(5))),
                        new CompoundStatement(new DeclarationStatement("b", new IntType()),
                                new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(4))),
                                        new CompoundStatement(new IfElseStatement(new RelationalExpression(new VariableEvaluation("a"), new VariableEvaluation("b"), "<="),
                                                new PrintStatement(new VariableEvaluation("a")), new PrintStatement(new VariableEvaluation("b"))),
                                                new IfElseStatement(new RelationalExpression(new VariableEvaluation("b"), new VariableEvaluation("a"), "!="),
                                                        new PrintStatement(new ValueExpression(new StringValue("Different"))),
                                                        new PrintStatement(new ValueExpression(new StringValue("Equal")))))))));

        iStatement ex6 = new CompoundStatement(new DeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableEvaluation("v"), new ValueExpression(new BoolValue(false)), ">"),
                                new CompoundStatement(new PrintStatement(new VariableEvaluation("v")), new AssignmentStatement("v",new ArithmeticExpression(new VariableEvaluation("v"), new ValueExpression(new IntValue(1)), '-')))),
                                new PrintStatement(new VariableEvaluation("v")))));
        ///Heap use
        iStatement ex7 = new CompoundStatement(new DeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new DeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewAllocationStatement("a", new VariableEvaluation("v")),
                                        new CompoundStatement(new PrintStatement(new VariableEvaluation("v")), new PrintStatement(new VariableEvaluation("a")))))));

        ///ReadHeapExpression
        iStatement ex8 = new CompoundStatement(new DeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new DeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewAllocationStatement("a", new VariableEvaluation("v")),
                                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableEvaluation("v"))),
                                                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableEvaluation("a"))), new ValueExpression(new IntValue(5)), '+')))))));

        ///WriteHeapStatement
        iStatement ex9 = new CompoundStatement(new DeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableEvaluation("v"))),
                                new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableEvaluation("v")), new ValueExpression(new IntValue(5)), '+'))))));

        ///Garbage Collector
        iStatement ex10 = new CompoundStatement(new DeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new DeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewAllocationStatement("a", new VariableEvaluation("v")),
                                        new CompoundStatement(new NewAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableEvaluation("a")))))))));

        ///threads
        iStatement ex11 = new CompoundStatement(new DeclarationStatement("v", new IntType()),
                new CompoundStatement(new DeclarationStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableEvaluation("v")), new PrintStatement(new ReadHeapExpression(new VariableEvaluation("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableEvaluation("v")), new PrintStatement(new ReadHeapExpression(new VariableEvaluation("a")))))))));

        prgList.add(ex1);
        prgList.add(ex2);
        prgList.add(ex3);
        prgList.add(ex4);
        prgList.add(ex5);
        prgList.add(ex6);
        prgList.add(ex7);
        prgList.add(ex8);
        prgList.add(ex9);
        prgList.add(ex10);
        prgList.add(ex11);

        return FXCollections.observableArrayList(prgList);
    }

    @FXML
    public void runProgram(MouseEvent mouseEvent) {
        iStatement selectedStatement = programList.getSelectionModel().getSelectedItem();
        if (selectedStatement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error encountered!");
            alert.setContentText("No statement selected!");
            alert.showAndWait();
        } else {
            int id = programList.getSelectionModel().getSelectedIndex();
            try {
                selectedStatement.typeCheck(new szHashMap<>());
                ProgramState programState = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), selectedStatement);
                iRepository repository = new Repository("log" + (id + 1) + ".txt");
                repository.add(programState);
                Controller controller = new Controller(repository);
                __detailsService.setController(controller);
            } catch(DataStructureException | StatementException | ExpressionException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error encountered!");
                alert.setContentText(exception.getMessage());
                alert.showAndWait();
            }
        }

    }
}