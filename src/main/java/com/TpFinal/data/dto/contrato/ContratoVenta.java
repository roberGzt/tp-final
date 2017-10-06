package com.TpFinal.data.dto.contrato;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.TpFinal.data.dto.EstadoRegistro;
import com.TpFinal.data.dto.inmueble.Inmueble;
import com.TpFinal.data.dto.publicacion.PublicacionVenta;

import java.math.BigDecimal;
import java.sql.Blob;
import java.time.LocalDate;

@Entity
@Table(name="contratoVenta")
@PrimaryKeyJoinColumn(name="id")
public class ContratoVenta extends Contrato {
	
		@Column(name="precioVenta")
		private BigDecimal precioVenta;
		
		@OneToOne(cascade=CascadeType.ALL)
		private PublicacionVenta publicacion;
	
		public ContratoVenta() {super();}
		
		private ContratoVenta(Builder b) {
			super(b.id, b.fechaCelebracion, b.documento, b.estadoRegistro, b.inmueble);
			this.precioVenta=b.precioVenta;
			this.publicacion =b.publicacionVenta;
		}
		


		public PublicacionVenta getPublicacionVenta() {
			return publicacion;
		}

		public void setPublicacionVenta(PublicacionVenta operacionVenta) {
			this.publicacion = operacionVenta;
		}

		public BigDecimal getPrecioVenta() {
			return precioVenta;
		}

		public void setPrecioVenta(BigDecimal precioVenta) {
			this.precioVenta = precioVenta;
		}



		public static class Builder{
			
			private Inmueble inmueble;
			private Long id;
			private LocalDate fechaCelebracion;
			private Blob documento;
			private BigDecimal precioVenta;
			private PublicacionVenta publicacionVenta;
			private EstadoRegistro estadoRegistro = EstadoRegistro.ACTIVO;
			
			public Builder setId(Long dato) {
				this.id=dato;
				return this;
			}
			
			public Builder setFechaCelebracion(LocalDate dato) {
				this.fechaCelebracion=dato;
				return this;
			}
			
			public Builder setDocumento(Blob dato) {
				this.documento=dato;
				return this;
			}
			
		
			public Builder setPrecioVenta(BigDecimal dato) {
				this.precioVenta=dato;
				return this;
			}
			
			public Builder setPublicacionVenta(PublicacionVenta dato) {
				this.publicacionVenta =dato;
				return this;
			}
			
			public Builder setEstadoRegistro(EstadoRegistro estadoRegistro) {
			    this.estadoRegistro = estadoRegistro;
			    return this;
			}
			
			public Builder setInmueble(Inmueble inmueble) {
			    this.inmueble = inmueble;
			    return this;
			}
			
			public ContratoVenta build() {
				return new ContratoVenta(this);
			}
		}
	
	
}
