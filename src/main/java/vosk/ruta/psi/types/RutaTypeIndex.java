package vosk.ruta.psi.types;

import java.util.HashMap;

public class RutaTypeIndex {
    private HashMap<String,RutaType> types = new HashMap<>();

    public RutaType getByName(String name){
        RutaType rutaType = types.get(name);
        if(!rutaType.getWhereTofind().isValid()){
            deleteByName(name);
            return null;
        }
        return rutaType;
    }
    public void put(RutaType type){
        types.put(type.getName(),type);
    }
    public void deleteByName(String name){
        types.remove(name);
    }
}
