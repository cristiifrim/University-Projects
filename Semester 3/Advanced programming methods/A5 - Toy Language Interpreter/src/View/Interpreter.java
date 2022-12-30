package View;

import Controller.Controller;
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
import Repository.Repository;
import Repository.iRepository;

public class Interpreter {
    public static void main(String args[]) {

        iStatement ex1= new CompoundStatement(new DeclarationStatement("v",new IntType()),
                        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableEvaluation("v"))));

        ProgramState prg1 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex1);
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

        ProgramState prg2 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex2);
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

        ProgramState prg3 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex3);
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

        ProgramState prg4 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex4);
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
        ProgramState prg5 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex5);
        iRepository repo5= new Repository("log5.txt");
        repo5.add(prg5);
        Controller ctr5 = new Controller(repo5);

        iStatement ex6 = new CompoundStatement(new DeclarationStatement("v", new IntType()),
                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new RelationalExpression(new VariableEvaluation("v"), new ValueExpression(new IntValue(0)), ">"),
                                new CompoundStatement(new PrintStatement(new VariableEvaluation("v")), new AssignmentStatement("v",new ArithmeticExpression(new VariableEvaluation("v"), new ValueExpression(new IntValue(1)), '-')))),
                                new PrintStatement(new VariableEvaluation("v")))));

        ProgramState prg6 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex6);
        iRepository repo6 = new Repository("log6.txt");
        repo6.add(prg6);
        Controller ctr6 = new Controller(repo6);

        ///Heap use
        iStatement ex7 = new CompoundStatement(new DeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new DeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewAllocationStatement("a", new VariableEvaluation("v")),
                                        new CompoundStatement(new PrintStatement(new VariableEvaluation("v")), new PrintStatement(new VariableEvaluation("a")))))));
        ProgramState prg7 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex7);
        iRepository repo7 = new Repository("log7.txt");
        repo7.add(prg7);
        Controller ctr7 = new Controller(repo7);

        ///ReadHeapExpression
        iStatement ex8 = new CompoundStatement(new DeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new DeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewAllocationStatement("a", new VariableEvaluation("v")),
                                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableEvaluation("v"))),
                                                new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableEvaluation("a"))), new ValueExpression(new IntValue(5)), '+')))))));
        ProgramState prg8 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex8);
        iRepository repo8 = new Repository("log8.txt");
        repo8.add(prg8);
        Controller ctr8 = new Controller(repo8);

        ///WriteHeapStatement
        iStatement ex9 = new CompoundStatement(new DeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new ReadHeapExpression(new VariableEvaluation("v"))),
                                new CompoundStatement(new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableEvaluation("v")), new ValueExpression(new IntValue(5)), '+'))))));
        ProgramState prg9 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex9);
        iRepository repo9 = new Repository("log9.txt");
        Controller ctr9 = new Controller(repo9);

        ///Garbage Collector
        iStatement ex10 = new CompoundStatement(new DeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(new NewAllocationStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new DeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(new NewAllocationStatement("a", new VariableEvaluation("v")),
                                        new CompoundStatement(new NewAllocationStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(new VariableEvaluation("a")))))))));

        ProgramState prg10 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex10);
        iRepository repo10 = new Repository("log10.txt");
        repo10.add(prg10);
        Controller ctr10 = new Controller(repo10);

        ///threads
        iStatement ex11 = new CompoundStatement(new DeclarationStatement("v", new IntType()),
                new CompoundStatement(new DeclarationStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new NewAllocationStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableEvaluation("v")), new PrintStatement(new ReadHeapExpression(new VariableEvaluation("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableEvaluation("v")), new PrintStatement(new ReadHeapExpression(new VariableEvaluation("a")))))))));

        ProgramState prg11 = new ProgramState(new szStack<>(), new szHashMap<>(), new szList<>(), new szHashMap<>(), new szHeap(), ex11);
        iRepository repo11 = new Repository("log11.txt");
        repo11.add(prg11);
        Controller ctr11 = new Controller(repo11);


        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "Exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExampleCommand("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExampleCommand("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExampleCommand("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExampleCommand("9", ex9.toString(), ctr9));
        menu.addCommand(new RunExampleCommand("10", ex10.toString(), ctr10));
        menu.addCommand(new RunExampleCommand("11", ex11.toString(), ctr11));

        menu.show();
    }
}
