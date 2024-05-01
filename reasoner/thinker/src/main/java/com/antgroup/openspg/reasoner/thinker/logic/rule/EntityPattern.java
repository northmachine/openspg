package com.antgroup.openspg.reasoner.thinker.logic.rule;

import com.antgroup.openspg.reasoner.thinker.logic.graph.Element;
import com.antgroup.openspg.reasoner.thinker.logic.graph.Entity;
import java.util.Objects;

public class EntityPattern implements ClauseEntry {
  private Entity entity;

  @Override
  public Element toElement() {
    return entity;
  }

  public EntityPattern() {}

  public EntityPattern(Entity entity) {
    this.entity = entity;
  }

  /**
   * Getter method for property <tt>entity</tt>.
   *
   * @return property value of entity
   */
  public Entity getEntity() {
    return entity;
  }

  /**
   * Setter method for property <tt>entity</tt>.
   *
   * @param entity value to be assigned to property entity
   */
  public void setEntity(Entity entity) {
    this.entity = entity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof EntityPattern)) {
      return false;
    }
    EntityPattern that = (EntityPattern) o;
    return Objects.equals(entity, that.entity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(entity);
  }
}