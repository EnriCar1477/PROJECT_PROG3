package pe.edu.pucp.kirusmile.services;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import pe.edu.pucp.kirusmile.dbmanager.DBManager;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@Path("reportes")
public class ReporteRS {
    // 1. REPORTE: HOJA CLÍNICA
    @GET
    @Path("hojaClinica/{idDetalle}")
    @Produces("application/pdf")
    public Response generarHojaClinicaPdf(@PathParam("idDetalle") int idDetalle) {
        Connection conexion = null;
        try {
            // Obtenemos la conexión a MySQL
            conexion = DBManager.getInstance().getConnection();

            // Leemos el archivo compilado .jasper
            InputStream inputStream = getClass().getResourceAsStream("/reportes/HojaClinica.jasper");

            // Pasamos los parámetros
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_DETALLE", idDetalle);

            // Llenamos el reporte usando la conexión a la BD en lugar de un DataSource
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, conexion);

            // Exportamos a PDF
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=HojaClinica_" + idDetalle + ".pdf")
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("Error al generar Hoja Clínica: " + e.getMessage()).build();
        } finally {
            try { if (conexion != null) conexion.close(); } catch (Exception e) { }
        }
    }

    // 2. REPORTE: RECETA PACIENTE
    @GET
    @Path("receta/{idDetalle}")
    @Produces("application/pdf")
    public Response generarRecetaPacientePdf(@PathParam("idDetalle") int idDetalle) {
        Connection conexion = null;
        try {
            conexion = DBManager.getInstance().getConnection();
            InputStream inputStream = getClass().getResourceAsStream("/reportes/RecetaPaciente.jasper");

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_DETALLE", idDetalle);

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, conexion);
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=Receta_" + idDetalle + ".pdf")
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("Error al generar Receta: " + e.getMessage()).build();
        } finally {
            try { if (conexion != null) conexion.close(); } catch (Exception e) { }
        }
    }

    // 3. REPORTE: ESPECIALIDADES (GRÁFICO BARRAS)
    @GET
    @Path("especialidades")
    @Produces("application/pdf")
    public Response generarReporteEspecialidadesPdf() {
        Connection conexion = null;
        try {
            conexion = DBManager.getInstance().getConnection();
            InputStream inputStream = getClass().getResourceAsStream("/reportes/ReporteEspecialidades.jasper");

            // Este reporte no necesita parámetros dinámicos, pero requiere el mapa
            Map<String, Object> parametros = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, conexion);
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=Reporte_Especialidades.pdf")
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("Error al generar Reporte Especialidades: " + e.getMessage()).build();
        } finally {
            try { if (conexion != null) conexion.close(); } catch (Exception e) { }
        }
    }

    // 4. REPORTE: PAGOS Y AUDITORÍA (LISTA + PIE CHART)
    @GET
    @Path("pagos")
    @Produces("application/pdf")
    public Response generarReportePagosPdf() {
        Connection conexion = null;
        try {
            conexion = DBManager.getInstance().getConnection();
            InputStream inputStream = getClass().getResourceAsStream("/reportes/ReportePagos.jasper");

            Map<String, Object> parametros = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, conexion);
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return Response.ok(pdf)
                    .header("Content-Disposition", "attachment; filename=Auditoria_Pagos.pdf")
                    .build();

        } catch (Exception e) {
            return Response.serverError().entity("Error al generar Reporte de Pagos: " + e.getMessage()).build();
        } finally {
            try { if (conexion != null) conexion.close(); } catch (Exception e) { }
        }
    }
}
