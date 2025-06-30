package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.*;

public class Generator {

	private static final String INDENT = "  ";

	/**
	 * Converts the given Abstract Syntax Tree (AST) into a CSS string.
	 *
	 * Only Stylerule nodes are processed; other node types are ignored.
	 *
	 * @param ast The AST representing the ICSS input.
	 * @return A string containing the generated CSS code.
	 */
	public String generate(AST ast) {
		StringBuilder cssBuilder = new StringBuilder();

		for (ASTNode node : ast.root.getChildren()) {
			if (node instanceof Stylerule stylerule) {
				appendStylerule(cssBuilder, stylerule);
			}
		}

		return cssBuilder.toString();
	}

	/**
	 * Appends a single stylerule block to the CSS output.
	 *
	 * Includes all valid declarations within the rule.
	 *
	 * @param sb The StringBuilder used to construct the CSS output.
	 * @param stylerule The stylerule to convert into CSS.
	 */
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

	/**
	 * Builds a comma-separated string of selectors for a stylerule.
	 *
	 * @param stylerule The stylerule whose selectors are being converted.
	 * @return A single string representing the selector line in CSS.
	 */
	private String generateSelectorLine(Stylerule stylerule) {
		return String.join(", ",
				stylerule.selectors.stream()
						.map(Selector::toString)
						.toArray(String[]::new)
		);
	}

	/**
	 * Appends a CSS declaration (property and value) to the CSS output.
	 *
	 * Skips the declaration if the property or expression is null.
	 *
	 * @param sb The StringBuilder used to construct the CSS output.
	 * @param declaration The declaration to convert into a CSS line.
	 */
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
