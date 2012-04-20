package ar.com.mitnick.business.servicios;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mitnick.persistence.entities.Cliente;

public class ClienteServicioTest {

    private static Validator validator;

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void nombreIsNull() {
        Cliente cliente = new Cliente();
        //cliente.setNombre("Lucas");
        cliente.setApellido("García");
        cliente.setDocumento("31115019");
        cliente.setCuit("20-31115019-8");
        cliente.setTelefono("12321321321");
        cliente.setEmail("lukasg_04@hotmail.com");

        Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(cliente);
        
        assertEquals(1, constraintViolations.size());
        assertEquals("{error.entity.cliente.nombre.null}", constraintViolations.iterator().next().getMessageTemplate());
    }

}
