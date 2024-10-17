package edu.icet.repository;

import edu.icet.entity.SupplierEntity;
import javafx.collections.ObservableList;

public interface CrudDao <T,S> extends SuperDao{
    boolean save(T t);

    ObservableList<T>searchAll();

    boolean update(T t);

    boolean delete(S s);

    T search(S s);

    S getLatestId();

    T searchById (S s);
}
