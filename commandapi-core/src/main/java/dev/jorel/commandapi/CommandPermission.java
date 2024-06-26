/*******************************************************************************
 * Copyright 2018, 2020 Jorel Ali (Skepter) - MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
package dev.jorel.commandapi;

import java.util.Objects;
import java.util.Optional;

/**
 * A representation of permission nodes for commands. Represents permission
 * nodes, being op and having all permissions
 */
public class CommandPermission {

	private enum PermissionNode {
		/**
		 * Command can be run with no permissions
		 */
		NONE,

		/**
		 * A player that has to be an operator to run a command
		 */
		OP;
	}

	/**
	 * Command can be run with no permissions
	 */
	public static final CommandPermission NONE = new CommandPermission(PermissionNode.NONE);

	/**
	 * A player that has to be an operator to run a command
	 */
	public static final CommandPermission OP = new CommandPermission(PermissionNode.OP);

	/**
	 * Generates a new CommandPermission from a permission node
	 * 
	 * @param permission the permission node
	 * @return a new CommandPermission
	 */
	public static CommandPermission fromString(String permission) {
		return new CommandPermission(permission);
	}

	private boolean negated = false;

	private String permission;

	private PermissionNode permissionNode;

	/**
	 * Represents either no permission or OP status in order to run a command
	 * 
	 * @param permissionNode The enumerated type of what permission is required to
	 *                       run this command
	 */
	private CommandPermission(PermissionNode permissionNode) {
		this.permission = null;
		this.permissionNode = permissionNode;
	}

	/**
	 * Represents a single permission required to execute a command
	 * 
	 * @param permission The permission node the sender requires to run this command
	 */
	private CommandPermission(String permission) {
		this.permission = permission;
		this.permissionNode = null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CommandPermission)) {
			return false;
		}
		CommandPermission other = (CommandPermission) obj;
		return negated == other.negated && Objects.equals(permission, other.permission) && permissionNode == other.permissionNode;
	}

	@Override
	public int hashCode() {
		return Objects.hash(negated, permission, permissionNode);
	}

	/**
	 * Returns a human-readable string of this CommandPermission
	 * 
	 * @return a human-readable string of this CommandPermission
	 */
	@Override
	public String toString() {
		final String result;
		if (permissionNode != null) {
			if (permissionNode == PermissionNode.OP) {
				result = "OP";
			} else {
				result = "NONE";
			}
		} else {
			result = permission;
		}
		return (negated ? "not " : "") + result;
	}

	/**
	 * Returns the custom permission in string form if set
	 * 
	 * @return An {@code Optional<String>} with the custom permission or an empty {@code Optional} if not set
	 */
	public Optional<String> getPermission() {
		return Optional.ofNullable(this.permission);
	}

	/**
	 * Returns if the permission is negated
	 * 
	 * @return the permission's negation state
	 */
	public boolean isNegated() {
		return this.negated;
	}

	PermissionNode getPermissionNode() {
		return this.permissionNode;
	}

	CommandPermission negate() {
		this.negated = true;
		return this;
	}

}
