package com.TpFinal.view.component;


import com.TpFinal.data.dto.contrato.Contrato;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;


public class DownloadButton extends Button {
    StreamResource sr;
    FileDownloader fileDownloader;
    @Deprecated
    public DownloadButton(String buttonName,StreamResource streamResource){
        super(buttonName);
        sr =streamResource;
        fileDownloader = new FileDownloader(sr);
        fileDownloader.extend(this);
        addStyleName(ValoTheme.BUTTON_TINY);
    }
   /* @Deprecated
    public DownloadButton(String buttonName,String filename){
        super(buttonName);
        fileDownloader = new FileDownloader(fromPathtoSR(filename));
        fileDownloader.extend(this);
        addStyleName(ValoTheme.BUTTON_TINY);

    }*/
    public DownloadButton(){
        super();
        this.setIcon(VaadinIcons.DOWNLOAD);
        this.setStyleName(ValoTheme.BUTTON_BORDERLESS);

    }
    public DownloadButton(String filename){
        super();
        fileDownloader = new FileDownloader(fromPathtoSR(filename));
        fileDownloader.extend(this);
        this.setIcon(VaadinIcons.DOWNLOAD);
        this.setStyleName(ValoTheme.BUTTON_BORDERLESS);

    }
    public void setFile(String filename){
        fileDownloader = new FileDownloader(fromPathtoSR(filename));
        fileDownloader.extend(this);
    }




    private StreamResource fromPathtoSR(String filename) {

        return new StreamResource(new StreamResource.StreamSource() {
            public InputStream getStream() {
                InputStream is = null;
                try {
                    is = new FileInputStream("Files"+ File.separator+filename);
                } catch (FileNotFoundException e) {
                    System.err.println("No se ha encontrado el archivo a descargar");
                }
                return is;
            }
        }, filename);

    }
    
    public void setFile(Contrato contrato, String nombreArchivoConExtension) {
    	@SuppressWarnings("serial")
		StreamSource ss = new StreamSource() {
			@Override
			public InputStream getStream() {
				InputStream is=null;
				try {
					Blob docBlob=contrato.getDocumento();
					byte[] docBlobBytes = docBlob.getBytes(1, (int) docBlob.length());
					is = new ByteArrayInputStream(docBlobBytes);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return is;
			}
		};
    	StreamResource sr= new StreamResource(ss, nombreArchivoConExtension);
    	fileDownloader = new FileDownloader(sr);
    	fileDownloader.extend(this);
    }
    
    //Esto lo pasamos al FileDownloader de vaadin
    public StreamResource getDocStreamResource(Contrato contrato, String nombreArchivoConExtension) {
    	@SuppressWarnings("serial")
		StreamSource ss = new StreamSource() {
			@Override
			public InputStream getStream() {
				InputStream is=null;
				try {
					Blob docBlob=contrato.getDocumento();
					byte[] docBlobBytes = docBlob.getBytes(1, (int) docBlob.length());
					is = new ByteArrayInputStream(docBlobBytes);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return is;
			}
		};
    	StreamResource sr= new StreamResource(ss, nombreArchivoConExtension);
    	return sr;
    }
}
