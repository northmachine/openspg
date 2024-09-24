/*
 * Copyright 2023 OpenSPG Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 */

package com.antgroup.openspg.reasoner.thinker.engine;

import com.antgroup.openspg.reasoner.thinker.TripleStore;
import com.antgroup.openspg.reasoner.thinker.logic.graph.Element;
import com.antgroup.openspg.reasoner.thinker.logic.graph.Entity;
import com.antgroup.openspg.reasoner.thinker.logic.graph.Triple;
import com.antgroup.openspg.reasoner.thinker.logic.graph.Value;
import java.util.*;
import java.util.stream.Collectors;

public class MemTripleStore implements TripleStore {
  private Map<Element, List<Triple>> sToTriple;
  private Map<Element, List<Triple>> oToTriple;

  public MemTripleStore() {
    this.sToTriple = new HashMap<>();
    this.oToTriple = new HashMap<>();
  }

  @Override
  public void init(Map<String, String> param) {}

  @Override
  public Collection<Element> find(Triple pattern) {
    List<Element> elements = new LinkedList<>();
    elements.addAll(findTriple(pattern));
    return elements;
  }

  private Collection<Element> findTriple(Triple tripleMatch) {
    Triple t = (Triple) tripleMatch.cleanAlias();
    List<Element> elements = new LinkedList<>();
    if (t.getSubject() instanceof Entity || t.getSubject() instanceof Triple) {
      for (Triple tri : sToTriple.getOrDefault(t.getSubject(), new LinkedList<>())) {
        if (t.matches(tri)) {
          elements.add(tri);
        }
      }
    } else if (t.getObject() instanceof Entity) {
      for (Triple tri : oToTriple.getOrDefault(t.getObject(), new LinkedList<>())) {
        if (t.matches(tri)) {
          elements.add(tri);
        }
      }
    } else {
      for (Triple tri :
          sToTriple.values().stream().flatMap(Collection::stream).collect(Collectors.toList())) {
        if (t.matches(tri)) {
          elements.add(tri);
        }
      }
    }
    return elements;
  }

  @Override
  public void addTriple(Triple triple) {
    Triple t = (Triple) triple.cleanAlias();
    List<Triple> sTriples =
        sToTriple.computeIfAbsent(t.getSubject().cleanAlias(), (k) -> new LinkedList<>());
    sTriples.add(t);
    if (!(t.getObject() instanceof Value)) {
      List<Triple> oTriples = oToTriple.computeIfAbsent(t.getObject(), (k) -> new LinkedList<>());
      oTriples.add(t);
    }
  }

  @Override
  public void clear() {
    this.sToTriple.clear();
    this.oToTriple.clear();
  }
}
