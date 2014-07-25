/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.devtools.j2objc.ast;

import java.util.List;

/**
 * Node type for a class or interface declaration.
 */
public class TypeDeclaration extends AbstractTypeDeclaration {

  private boolean isInterface = false;
  private ChildLink<Type> superclassType = ChildLink.create(this);
  private ChildList<Type> superInterfaceTypes = ChildList.create(this);

  public TypeDeclaration(org.eclipse.jdt.core.dom.TypeDeclaration jdtNode) {
    super(jdtNode);
    superclassType.set((Type) TreeConverter.convert(jdtNode.getSuperclassType()));
    isInterface = jdtNode.isInterface();
    for (Object superInterface : jdtNode.superInterfaceTypes()) {
      superInterfaceTypes.add((Type) TreeConverter.convert(superInterface));
    }
  }

  public TypeDeclaration(TypeDeclaration other) {
    super(other);
    isInterface = other.isInterface();
    superclassType.copyFrom(other.getSuperclassType());
    superInterfaceTypes.copyFrom(other.getSuperInterfaceTypes());
  }

  public boolean isInterface() {
    return isInterface;
  }

  public Type getSuperclassType() {
    return superclassType.get();
  }

  public List<Type> getSuperInterfaceTypes() {
    return superInterfaceTypes;
  }

  @Override
  protected void acceptInner(TreeVisitor visitor) {
    if (visitor.visit(this)) {
      name.accept(visitor);
      superclassType.accept(visitor);
      superInterfaceTypes.accept(visitor);
      bodyDeclarations.accept(visitor);
    }
    visitor.endVisit(this);
  }

  @Override
  public TypeDeclaration copy() {
    return new TypeDeclaration(this);
  }
}