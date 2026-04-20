import java.util.List;
import java.util.ArrayList;

public class GestorHistorialMedico{
	
	private List<HistorialMedico> listaHistorialMedico;
	
	public GestorHistorialMedico(){
		listaHistorialMedico=new ArrayList<HistorialMedico>();
		//listaHistorialMedico=new ArrayList<>();
	}
	
	//getter y setter
	
	public List<HistorialMedico> getListaHistorialMedico(){
		return new ArrayList<>(listaHistorialMedico);
	}
	
	public void setListaHistorialMedico(List<HistorialMedico>listaHistorialMedico){
		this.listaHistorialMedico=new ArrayList<>(listaHistorialMedico);
	}
	
	
	
	public void agregarHistorialMedico(){//si es nuevo paciente
		
	}
	
	public void eliminarHistorialMedico(){//si esta betado
		
	}
	
	public void modificarHistorialMedico(){//si agregamos alguna observacion más
		
	}
	
	/*
	public void listarHistorialMedico(String dniPaciente){
		
	}
	*/
	
}