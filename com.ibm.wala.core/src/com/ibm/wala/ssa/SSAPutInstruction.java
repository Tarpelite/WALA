/*******************************************************************************
 * Copyright (c) 2002 - 2006 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.wala.ssa;

import com.ibm.wala.types.FieldReference;
import com.ibm.wala.util.debug.Assertions;

/**
 * Instruction for a putfield or putstatic
 * 
 * @author sfink
 * 
 */
public class SSAPutInstruction extends SSAFieldAccessInstruction {

  private final int val;

  public SSAPutInstruction(int ref, int val, FieldReference field) {
    super(field, ref);
    this.val = val;
  }

  public SSAPutInstruction(int val, FieldReference field) {
    super(field, -1);
    this.val = val;
  }

  @Override
  public SSAInstruction copyForSSA(int[] defs, int[] uses) {
    if (isStatic())
      return new SSAPutInstruction(uses == null ? val : uses[0], getDeclaredField());
    else
      return new SSAPutInstruction(uses == null ? getRef() : uses[0], uses == null ? val : uses[1], getDeclaredField());
  }

  @Override
  public String toString(SymbolTable symbolTable, ValueDecorator d) {
    if (isStatic()) {
      return "putstatic " + getValueString(symbolTable, d, val) + " " + getDeclaredField();
    } else {
      return "putfield " + getValueString(symbolTable, d, getRef()) + " = " + getValueString(symbolTable, d, val) + " "
          + getDeclaredField();
    }
  }

  /**
   * @see com.ibm.wala.ssa.SSAInstruction#visit(IVisitor)
   * @throws IllegalArgumentException  if v is null
   */
  @Override
  public void visit(IVisitor v) {
    if (v == null) {
      throw new IllegalArgumentException("v is null");
    }
    v.visitPut(this);
  }

  /**
   * @see com.ibm.wala.ssa.SSAInstruction#getNumberOfUses()
   */
  @Override
  public int getNumberOfUses() {
    return isStatic()? 1 : 2;
  }

  /**
   * @see com.ibm.wala.ssa.SSAInstruction#getUse(int)
   */
  @Override
  public int getUse(int j) {
    if (Assertions.verifyAssertions)
      Assertions._assert(j == 0 || (!isStatic() && j == 1));
    return (j == 0 && !isStatic()) ? getRef() : val;
  }

  public int getVal() {
    return val;
  }

  @Override
  public int hashCode() {
    return val * 9929 ^ 2063;
  }

  /*
   * @see com.ibm.wala.ssa.Instruction#isFallThrough()
   */
  @Override
  public boolean isFallThrough() {
    return true;
  }
}
