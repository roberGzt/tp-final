package com.TpFinal.view.publicacion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.TpFinal.dto.publicacion.Publicacion;

public class FiltroPublicacion {
		private Predicate<Publicacion> inmueble = publicacion -> true;
		private Predicate<Publicacion> propietario = publicacion -> true;
		private Predicate<Publicacion> fechaDesde = publicacion -> true;
		private Predicate<Publicacion> fechaHasta = publicacion -> true;
		private Predicate<Publicacion> anio = publicacion -> true;
		private Predicate<Publicacion> mes = publicacion -> true;
		private Predicate<Publicacion> estadoPublicacion = publicacion -> true;
		private Predicate<Publicacion> operacion = publicacion -> true;
		private List<Predicate<Publicacion>> filtros = new ArrayList<>();
		private Predicate<Publicacion> filtroCompuesto;

		public FiltroPublicacion() {
			filtros.addAll(Arrays.asList(propietario,fechaHasta, fechaDesde, inmueble, estadoPublicacion, operacion));
			filtroCompuesto = publicacion -> true;
		}

		public Predicate<Publicacion> getFiltroCompuesto() {
			return filtroCompuesto;
		}

		private void actualizarComposicion() {
			filtros.clear();
			filtros.addAll(Arrays.asList(propietario, fechaDesde, inmueble, estadoPublicacion));
			filtroCompuesto = filtros.stream().reduce(publicacion -> true, Predicate::and);
		}

		@Deprecated
		public void clearFiltro(Predicate<Publicacion> filtro) {
			filtro = contrato -> true;
		}

		public Predicate<Publicacion> getPropietario() {
			return propietario;
		}

		public void setPropietario(Predicate<Publicacion> propietario) {
			this.propietario = propietario;
			actualizarComposicion();
		}    

		public Predicate<Publicacion> getFechaDesde() {
			return fechaDesde;
		}
		
		public Predicate<Publicacion> getFechaHasta() {
			return fechaHasta;
		}

		public void setFechaHasta(Predicate<Publicacion> fechaHasta) {
			this.fechaHasta = fechaHasta;
		}

		public void setFechaDesde(Predicate<Publicacion> tipo) {
			this.fechaDesde = tipo;
			actualizarComposicion();
		}

		public Predicate<Publicacion> getInmueble() {
			return inmueble;
		}

		public void setInmueble(Predicate<Publicacion> inmueble) {
			this.inmueble = inmueble;
			actualizarComposicion();
		}

		public Predicate<Publicacion> getEstado() {
			return estadoPublicacion;
		}

		public void setEstado(Predicate<Publicacion> estado) {
			this.estadoPublicacion = estado;
			actualizarComposicion();
		}

		public List<Predicate<Publicacion>> getTodosLosFiltros() {
			return filtros;
		}
		
		public Predicate<Publicacion> getOperacion() {
			return operacion;
		}

		public void setOperacion(Predicate<Publicacion> operacion) {
			this.operacion = operacion;
		}
		
		public Predicate<Publicacion> getAnio() {
			return anio;
		}

		public Predicate<Publicacion> getMes() {
			return mes;
		}

		public Predicate<Publicacion> getEstadoPublicacion() {
			return estadoPublicacion;
		}

		public void setAnio(Predicate<Publicacion> anio) {
			this.anio = anio;
		}

		public void setMes(Predicate<Publicacion> mes) {
			this.mes = mes;
		}

		public void setEstadoPublicacion(Predicate<Publicacion> estadoPublicacion) {
			this.estadoPublicacion = estadoPublicacion;
		}

		public void setTodosLosFiltros(List<Predicate<Publicacion>> todosLosFiltros) {
			this.filtros = todosLosFiltros;
			actualizarComposicion();
		}
		
		


}
