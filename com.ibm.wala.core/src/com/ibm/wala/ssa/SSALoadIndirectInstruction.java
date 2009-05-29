/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.wala.ssa;

import com.ibm.wala.util.debug.Assertions;

/**
 * A load from a pointer. 
 * 
 * v = *p
 */
public class SSALoadIndirectInstruction extends SSAAbstractUnaryInstruction {

  /**
   * @param lval the value number which is def'fed by this instruction.
   * @param addressVal the value number holding the pointer p deferenced (*p)
   */
  public SSALoadIndirectInstruction(int lval, int addressVal) {
    super(lval, addressVal);
  }

  @Override
  public SSAInstruction copyForSSA(SSAInstructionFactory insts, int[] defs, int[] uses) {
    Assertions.UNREACHABLE("not implemented");
    return null;
  }

  @Override
  public String toString(SymbolTable symbolTable) {
    return getValueString(symbolTable, getDef(0)) + " =  *"  + getValueString(symbolTable, getUse(0));
  }

  @Override
  public void visit(IVisitor v) {
  }

}
