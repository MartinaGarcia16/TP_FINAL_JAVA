package logic;

import java.util.LinkedList;
import dataBase.DataValor_Especialidad;
import entities.Valor_especialidad;

public class Valor_EspecialidadController {
	
	private DataValor_Especialidad dve;
	
	public Valor_EspecialidadController() {
		dve = new DataValor_Especialidad();
	}
			
	public LinkedList<Valor_especialidad> getValores() {
		return dve.getValoresActuales();
	}

	public Valor_especialidad getValorPorCodigo(int id) {
		return dve.getValorPorCodigo(id);
	}
}
