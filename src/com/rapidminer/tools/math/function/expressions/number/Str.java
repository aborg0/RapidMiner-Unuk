/*
 *  RapidMiner
 *
 *  Copyright (C) 2001-2013 by Rapid-I and the contributors
 *
 *  Complete list of developers available at our web site:
 *
 *       http://rapid-i.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.tools.math.function.expressions.number;

import java.util.Stack;

import org.nfunk.jep.ParseException;
import org.nfunk.jep.function.PostfixMathCommand;

import com.rapidminer.tools.Tools;
import com.rapidminer.tools.math.function.UnknownValue;

/**
 * Returns a string representation of this number.
 * 
 * @author Ingo Mierswa
 */
public class Str extends PostfixMathCommand {

	public Str() {
		numberOfParameters = 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run(Stack stack) throws ParseException {
		checkStack(stack);// check the stack

		// initialize the result to the first argument
		Object object = stack.pop();

		// checking for unknown value
		if (object instanceof Double && ((Double) object).isNaN()) {
			stack.push(UnknownValue.UNKNOWN_NOMINAL);
			return;
		}
		if (!(object instanceof Number)) {
			throw new ParseException("Invalid argument type, must be (number)");
		}

		stack.push(Tools.formatIntegerIfPossible(((Number)object).doubleValue()));
	}
}
