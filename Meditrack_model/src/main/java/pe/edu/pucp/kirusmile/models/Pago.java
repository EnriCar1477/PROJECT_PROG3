package pe.edu.pucp.kirusmile.models;

import java.util.Date;

public class Pago {
    private int idPago;
    private double monto;
    private Date fechaHora;
    private String metodoPago;
    private int citaPagada;

    public Pago(int idPago, double monto, Date fechaHora, String metodoPago,CitaMedica citaPagada){
        this.idPago = idPago;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.metodoPago = metodoPago;
        this.citaPagada = citaPagada.getIdCita();
    }

    public int getIdPago(){ return this.idPago; }
    public void setIdPago(int idPago){ this.idPago = idPago; }

    public double getMonto(){ return this.monto; }
    public void getMonto(double monto){ this.monto = monto; }

    public Date getFechaHora(){ return this.fechaHora; }
    public void setFechaHora(Date fechaHora){ this.fechaHora = fechaHora; }

    public String getMetodoPago(){ return this.metodoPago; }
    public void setMetodoPago(String metodoPago){ this.metodoPago = metodoPago; }

    public int getCitaPagada(){ return this.citaPagada; }
    public void setCitaPagada(int citaPagada){ this.citaPagada = citaPagada; }

    /*public void asignarMontoDesdeCita(CitaMedica cita){

    }*/

    public String obtenerResumen(){
        String cadena = "Resumen del pago: \nId del Pago: " + idPago + "\nMonto: " + monto + "\nFecha: " + fechaHora + "\nMetodo de Pago: " + metodoPago;
        return cadena;
    }
}
