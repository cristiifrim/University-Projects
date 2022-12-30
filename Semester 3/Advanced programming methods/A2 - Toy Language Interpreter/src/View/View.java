package View;

import Controller.Controller;
import Exceptions.DataStructureException;
import Exceptions.ExpressionException;
import Exceptions.StatementException;
import Model.DataStructures.*;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableEvaluation;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.iValue;
import Repository.iRepository;
import Repository.Repository;

import java.util.Objects;
import java.util.Scanner;

public class View {

    private final static Scanner console = new Scanner(System.in);

    private void menu() {
        System.out.println("---------------");
        System.out.println("*\t1. Input a program.");
        System.out.println("*\t0. Exit");
        System.out.println("---------------");
    }

    private void optionOne() throws DataStructureException, StatementException, ExpressionException {
        System.out.println("---------------");
        System.out.println("*\t1. int v; v=2;Print(v)");
        System.out.println("*\t2. int a;int b; a=2+3*5;b=a+1;Print(b)");
        System.out.println("*\t2. bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)");
        System.out.println("*\t0. Exit");
        System.out.println("---------------");

        System.out.println("Your option: ");
        String option = console.nextLine();

        if(Objects.equals(option, "0"))
            return;

        switch (option) {
            case "1" -> runProgramOne();
            case "2" -> runProgramTwo();
            case "3" -> runProgramThree();
            default -> System.out.println("Invalid input");
        }
    }

    private void runProgramThree() throws DataStructureException, StatementException, ExpressionException {
        iStatement ex3 = new CompoundStatement(new DeclarationStatement("a",new BoolType()),
                         new CompoundStatement(new DeclarationStatement("v", new IntType()),
                         new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                         new CompoundStatement(new IfElseStatement(new VariableEvaluation("a"),
                         new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                         new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                         new PrintStatement(new VariableEvaluation("v"))))));

        runStatement(ex3);
    }

    private void runProgramTwo() throws DataStructureException, StatementException, ExpressionException {
        iStatement ex2 = new CompoundStatement(new DeclarationStatement("a",new IntType()),
                         new CompoundStatement(new DeclarationStatement("b",new IntType()),
                         new CompoundStatement(new AssignmentStatement("a",
                         new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                         new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), '*'), '+')),
                         new CompoundStatement(new AssignmentStatement("b",
                         new ArithmeticExpression(new VariableEvaluation("a"), new ValueExpression(new IntValue(1)), '+')),
                         new PrintStatement(new VariableEvaluation("b"))))));
        runStatement(ex2);
    }

    private void runProgramOne() throws DataStructureException, StatementException, ExpressionException {
        iStatement ex1= new CompoundStatement(new DeclarationStatement("v",new IntType()),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableEvaluation("v"))));

        runStatement(ex1);
    }

    private void runStatement(iStatement statement) throws DataStructureException, StatementException, ExpressionException {
        iStack<iStatement>  stack = new szStack<>();
        iHashMap<String, iValue> hashmap = new szHashMap<>();
        iList<iValue> list = new szList<>();

        ProgramState state = new ProgramState(stack, hashmap, list, statement);
        iRepository repo = new Repository();

        repo.add(state);

        Controller service = new Controller(repo);

        System.out.println("Do you want to see all the execution steps?[Y or N]");

        String option = console.nextLine();
        service.setDisplay(Objects.equals(option, "Y"));

        service.runProgram();

    }

    public void run() {
        System.out.println("Hello user!");
        while(true) {
            menu();
            System.out.println("Your option: ");
            String option = console.nextLine();

            if(Objects.equals(option, "0"))
                break;
            try {
                if (Objects.equals(option, "1")) {
                    optionOne();
                }
                else
                    System.out.println("Invalid input");
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Goodbye!");
    }


}
