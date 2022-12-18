package ITEM21;

import java.util.Objects;
import java.util.function.Predicate;

public interface Collection {

    java.util.Collection collection = null;

    default boolean removeIf(Predicate<? super E> filter){
        Objects.requireNonNull(filter);
        boolean result = false;
        for(Iterable<E> it = iterator(); it.hasNext();){

        }
        return result;
    }

}
