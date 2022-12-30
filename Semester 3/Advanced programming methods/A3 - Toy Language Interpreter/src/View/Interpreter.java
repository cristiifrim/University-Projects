package View;

import Controller.Controller;
import Model.DataStructures.szHashMap;
import Model.DataStructures.szList;
import Model.DataStructures.szStack;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.ValueExpression;
import Model.Expressions.VariableEvaluation;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Repository.Repository;
import Repository.iRepository;

public class Interpreter {
    public static void main(String args[]) {

        iStatement ex1= new CompoundStatement(new DeclarationStatement("v",new IntType()),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableEvaluation("v"))));

        ProgramState prg1 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), ex1);
        iRepository repo1 = new Repository("log1.txt");
        repo1.add(prg1);
        Controller ctr1 = new Controller(repo1);

        iStatement ex2 = new CompoundStatement(new DeclarationStatement("a",new IntType()),
                         new CompoundStatement(new DeclarationStatement("b",new IntType()),
                         new CompoundStatement(new AssignmentStatement("a",
                         new ArithmeticExpression(new ValueExpression(new IntValue(2)),
                         new ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), '*'), '+')),
                         new CompoundStatement(new AssignmentStatement("b",
                         new ArithmeticExpression(new VariableEvaluation("a"), new ValueExpression(new IntValue(1)), '+')),
                         new PrintStatement(new VariableEvaluation("b"))))));

        ProgramState prg2 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), ex2);
        iRepository repo2 = new Repository("log2.txt");
        repo2.add(prg2);
        Controller ctr2 = new Controller(repo2);

        iStatement ex3 = new CompoundStatement(new DeclarationStatement("a",new BoolType()),
                         new CompoundStatement(new DeclarationStatement("v", new IntType()),
                         new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                         new CompoundStatement(new IfElseStatement(new VariableEvaluation("a"),
                         new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                         new AssignmentStatement("v", new ValueExpression(new IntValue(3)))),
                         new PrintStatement(new VariableEvaluation("v"))))));

        ProgramState prg3 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), ex3);
        iRepository repo3 = new Repository("log3.txt");
        repo3.add(prg3);
        Controller ctr3 = new Controller(repo3);

        iStatement ex4 = new CompoundStatement(new DeclarationStatement("varf", new StringType()),
                         new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                         new CompoundStatement(new OpenReadFileStatement(new VariableEvaluation("varf")),
                         new CompoundStatement(new DeclarationStatement("varc", new IntType()),
                         new CompoundStatement(new ReadFileStatement(new VariableEvaluation("varf"), "varc"),
                         new CompoundStatement(new PrintStatement(new VariableEvaluation("varc")),
                         new CompoundStatement(new ReadFileStatement(new VariableEvaluation("varf"), "varc"),
                         new CompoundStatement(new PrintStatement(new VariableEvaluation("varc")),
                         new CloseReadFileStatement(new VariableEvaluation("varf"))))))))));

        ProgramState prg4 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), ex4);
        iRepository repo4 = new Repository("log4.txt");
        repo4.add(prg4);
        Controller ctr4 = new Controller(repo4);


        /*
          int a; a=5;
          int b; b=6;
          if(a <= b) then print(a) else print(b)
          if(b != a) then print("Different) else print("Equal")
         */

        iStatement ex5 = new CompoundStatement(new DeclarationStatement("a", new IntType()),
                         new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new IntValue(5))),
                         new CompoundStatement(new DeclarationStatement("b", new IntType()),
                         new CompoundStatement(new AssignmentStatement("b", new ValueExpression(new IntValue(4))),
                         new CompoundStatement(new IfElseStatement(new RelationalExpression(new VariableEvaluation("a"), new VariableEvaluation("b"), "<="),
                         new PrintStatement(new VariableEvaluation("a")), new PrintStatement(new VariableEvaluation("b"))),
                         new IfElseStatement(new RelationalExpression(new VariableEvaluation("b"), new VariableEvaluation("a"), "!="),
                         new PrintStatement(new ValueExpression(new StringValue("Different"))),
                         new PrintStatement(new ValueExpression(new StringValue("Equal")))))))));
        ProgramState prg5 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), ex5);
        iRepository repo5= new Repository("log5.txt");
        repo5.add(prg5);
        Controller ctr5 = new Controller(repo5);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), ctr5));

        menu.show();
    }
}
