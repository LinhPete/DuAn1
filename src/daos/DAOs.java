/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daos;

import java.util.List;

/**
 *
 * @author ndhlt
 * @param <EntityType>
 * @param <KeyType>
 */
abstract public class DAOs<EntityType, KeyType> {
    abstract public int insert(EntityType e);
    abstract public int update(EntityType e);
    abstract public int delete(KeyType key);
    abstract public List<EntityType> selectAll();
    abstract public EntityType selectByID(KeyType key);
    abstract protected List<EntityType> selectBySql(String sql, Object... args);
}
