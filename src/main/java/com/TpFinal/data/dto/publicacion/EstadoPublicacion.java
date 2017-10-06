package com.TpFinal.data.dto.publicacion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 10/5/2017.
 */
public enum EstadoPublicacion { ACTIVA,TERMINADA;

    public static List<EstadoPublicacion> toList() {
        EstadoPublicacion[] tipos = EstadoPublicacion.values();
        List<EstadoPublicacion> ret = new ArrayList<>();
        for (EstadoPublicacion t : tipos) {
            ret.add(t);
        }
        return ret;
    }
}
