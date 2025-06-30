package nl.han.ica.icss.parser;


import nl.han.ica.datastructures.HANStack;
import nl.han.ica.datastructures.IHANStack;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.booloperations.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.AddOperation;
import nl.han.ica.icss.ast.operations.MultiplyOperation;
import nl.han.ica.icss.ast.operations.SubtractOperation;
import nl.han.ica.icss.ast.selectors.ClassSelector;
import nl.han.ica.icss.ast.selectors.IdSelector;
import nl.han.ica.icss.ast.selectors.TagSelector;

/**
 * This class extracts the ICSS Abstract Syntax Tree from the Antlr Parse tree. It builds the Abstract Syntax Tree (AST) from the ICCS parse tree.
 */
public class ASTListener extends ICSSBaseListener {
	
	//Accumulator attributes:
	private AST ast;

	//Use this to keep track of the parent nodes when recursively traversing the ast
	private IHANStack<ASTNode> currentContainer;

	public ASTListener() {
		this.ast = new AST();
		this.currentContainer = new HANStack<>();
		this.currentContainer.push(ast.root);
	}
    public AST getAST() {
        return this.ast;
    }

	@Override
	public void exitStylesheet(ICSSParser.StylesheetContext ctx) {
		this.currentContainer.pop();
	}

	@Override
	public void enterStyleRule(ICSSParser.StyleRuleContext ctx) {
		Stylerule styleRule = new Stylerule();
		addAndPush(styleRule);
	}

	@Override
	public void exitStyleRule(ICSSParser.StyleRuleContext ctx) {
		this.currentContainer.pop();
	}

	@Override
	public void enterVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		VariableAssignment variableAssignment = new VariableAssignment();
		addAndPush(variableAssignment);
	}

	@Override
	public void exitVariableAssignment(ICSSParser.VariableAssignmentContext ctx) {
		this.currentContainer.pop();
	}

	@Override
	public void enterVariableReference(ICSSParser.VariableReferenceContext ctx) {
		VariableReference variableReference = new VariableReference(ctx.getText());
		currentContainer.peek().addChild(variableReference);
	}

	@Override
	public void enterIfStatement(ICSSParser.IfStatementContext ctx) {
		IfClause ifClause = new IfClause();
		addAndPush(ifClause);
	}

	@Override
	public void exitIfStatement(ICSSParser.IfStatementContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterElseStatement(ICSSParser.ElseStatementContext ctx) {
		ElseClause elseClause = new ElseClause();
		addAndPush(elseClause);
	}

	@Override
	public void exitElseStatement(ICSSParser.ElseStatementContext ctx) {
		currentContainer.pop();
	}

	@Override
	public void enterSelector(ICSSParser.SelectorContext ctx) {
		Selector selector;

		if (ctx.LOWER_IDENT() != null) {
			selector = new TagSelector(ctx.LOWER_IDENT().getText());
		} else if (ctx.CLASS_IDENT() != null) {
			selector = new ClassSelector(ctx.CLASS_IDENT().getText());
		} else if (ctx.ID_IDENT() != null) {
			selector = new IdSelector(ctx.ID_IDENT().getText());
		} else {
			selector = new IdSelector(ctx.COLOR().getText());
		}

		currentContainer.peek().addChild(selector);
	}

	@Override
	public void enterScalarLiteral(ICSSParser.ScalarLiteralContext ctx) {
		ScalarLiteral scalarLiteral = new ScalarLiteral(ctx.getText());
		currentContainer.peek().addChild(scalarLiteral);
	}

	@Override
	public void enterPixelLiteral(ICSSParser.PixelLiteralContext ctx) {
		PixelLiteral pixelLiteral = new PixelLiteral(ctx.getText());
		currentContainer.peek().addChild(pixelLiteral);
	}

	@Override
	public void enterBoolLiteral(ICSSParser.BoolLiteralContext ctx) {
		BoolLiteral boolLiteral = new BoolLiteral(ctx.getText());
		currentContainer.peek().addChild(boolLiteral);
	}

	@Override
	public void enterPercentageLiteral(ICSSParser.PercentageLiteralContext ctx) {
		PercentageLiteral percentageLiteral = new PercentageLiteral(ctx.getText());
		currentContainer.peek().addChild(percentageLiteral);
	}

	@Override
	public void enterColorLiteral(ICSSParser.ColorLiteralContext ctx) {
		ColorLiteral colorLiteral = new ColorLiteral(ctx.getText());
		currentContainer.peek().addChild(colorLiteral);
	}

	@Override
	public void enterOperation(ICSSParser.OperationContext ctx) {
		if (ctx.getChildCount() < 3 || ctx.primaryExpression() != null) {
			return;
		}

		String operator = ctx.getChild(1).getText();
		if (!isArithmeticOperator(operator)) {
			return;
		}

		Operation operation;
		switch (operator) {
			case "*":
				operation = new MultiplyOperation();
				break;
			case "-":
				operation = new SubtractOperation();
				break;
			case "+":
				operation = new AddOperation();
				break;

			default:
				return;
		}

		addAndPush(operation);
	}


	@Override
	public void exitOperation(ICSSParser.OperationContext ctx) {
		if (ctx.getChildCount() < 3 || ctx.primaryExpression() != null) {
			return;
		}

		String operator = ctx.getChild(1).getText();
		if (!isArithmeticOperator(operator)) {
			return;
		}

		currentContainer.pop();
	}


	@Override
	public void enterBooleanExpression(ICSSParser.BooleanExpressionContext ctx) {
		if (ctx.NOT() != null && ctx.getChildCount() == 2) {
			addAndPush(new NotOperation());
			return;
		}

		if (ctx.getChildCount() < 3) {
			return;
		}

		String operator = ctx.getChild(1).getText();
		if (!isBooleanComparisonOperator(operator)) {
			return;
		}

		BooleanExpression booleanExpression;
		switch (operator) {
			case "==":
				booleanExpression = new EqualsOperation();
				break;
			case "&&":
				booleanExpression = new AndOperation();
				break;
			case "<":
				booleanExpression = new LessThanOperation();
				break;
			default:
				booleanExpression = new NotOperation();
		}

		addAndPush(booleanExpression);
	}

	@Override
	public void exitBooleanExpression(ICSSParser.BooleanExpressionContext ctx) {
		if (ctx.NOT() != null && ctx.getChildCount() == 2) {
			currentContainer.pop();
			return;
		}

		if (ctx.getChildCount() >= 3 && isBooleanComparisonOperator(ctx.getChild(1).getText())) {
			currentContainer.pop();
		}
	}

	@Override
	public void enterDeclaration(ICSSParser.DeclarationContext ctx) {
		Declaration declaration = new Declaration(ctx.propertyName().getText());
		addAndPush(declaration);
	}

	@Override
	public void exitDeclaration(ICSSParser.DeclarationContext ctx) {
		this.currentContainer.pop();
	}

	private boolean isArithmeticOperator(String operator) {
		return switch (operator) {
			case "*", "-", "+" -> true;
			default -> false;
		};
	}

	private boolean isBooleanComparisonOperator(String operator) {
		return switch (operator) {
			case "==", "&&", "<" -> true;
			default -> false;
		};
	}

	private void addAndPush(ASTNode node) {
		currentContainer.peek().addChild(node);
		currentContainer.push(node);
	}
}