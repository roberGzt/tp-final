package com.TpFinal.data.dao;

import com.TpFinal.data.conexion.ConexionHibernate;
import com.TpFinal.data.dao.interfaces.DAOContratoAlquiler;
import com.TpFinal.data.dto.contrato.ContratoAlquiler;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;

/**
 * Created by Max on 9/30/2017.
 */
public class DAOContratoAlquilerImpl extends DAOImpl<ContratoAlquiler> implements DAOContratoAlquiler{
    public DAOContratoAlquilerImpl() { super(ContratoAlquiler.class); }

    @Override
    public boolean saveContrato(ContratoAlquiler entidad, File doc) {
        boolean ret = false;
        FileInputStream docInputStream = null;
        Session session = ConexionHibernate.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Blob archivo = null;

            docInputStream = new FileInputStream(doc);
            archivo = Hibernate.getLobCreator(session).createBlob(docInputStream, doc.length());

            entidad.setDocumento(archivo);

            session.saveOrUpdate(entidad);
            tx.commit();
            ret = true;
        } catch (HibernateException | FileNotFoundException e) {
            System.err.println("Error al leer");
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
            if (docInputStream != null)
                try {
                    docInputStream.close();
                } catch (IOException e) {

                }
        }
        return ret;
    }
}