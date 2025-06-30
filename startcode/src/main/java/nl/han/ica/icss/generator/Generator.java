package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.*;

public class Generator {

	private static final String INDENT = "  ";

	public String generate(AST ast) {
		StringBuilder cssBuilder = new StringBuilder();

		for (ASTNode node : ast.root.getChildren()) {
			if (node instanceof Stylerule stylerule) {
				appendStylerule(cssBuilder, stylerule);
			}
		}

		return cssBuilder.toString();
	}


	private void appendStylerule(StringBuilder sb, Stylerule stylerule) {
		String selectorLine = generateSelectorLine(stylerule);
		sb.append(selectorLine).append(" {\n");

		for (ASTNode node : stylerule.getChildren()) {
			if (node instanceof Declaration declaration) {
				appendDeclaration(sb, declaration);
			}
		}

		sb.append("}\n");
	}

	private String generateSelectorLine(Stylerule stylerule) {
		return String.join(", ",
				stylerule.selectors.stream()
						.map(Selector::toString)
						.toArray(String[]::new)
		);
	}

	private void appendDeclaration(StringBuilder sb, Declaration declaration) {
		if (declaration.property != null && declaration.expression != null) {
			sb.append(INDENT)
					.append(declaration.property.name)
					.append(": ")
					.append(declaration.expression.toString())
					.append(";\n");
		}
	}
}
