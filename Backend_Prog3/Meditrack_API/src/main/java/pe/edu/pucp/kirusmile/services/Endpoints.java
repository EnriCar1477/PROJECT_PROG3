package pe.edu.pucp.kirusmile.services;

import pe.edu.pucp.kirusmile.BL.impl.*;
import pe.edu.pucp.kirusmile.BL.inter.*;
import pe.edu.pucp.kirusmile.dao.impl.*;
import pe.edu.pucp.kirusmile.models.*;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(
        serviceName = "Endpoints",
        targetNamespace = "http://services.kirusmile.pucp.edu.pe/"
)

public class Endpoints {

    @WebMethod(operationName = "loguear")
    public int loguear(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password) {
            IEmpleadoBL empleadoBL = new EmpleadoBLImpl();
            return empleadoBL.autenticar(username,password).getIdEmpleado();
    }

}
