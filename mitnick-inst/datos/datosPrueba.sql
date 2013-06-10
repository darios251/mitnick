-- ----------------------------
-- Records of parametro
-- ----------------------------
INSERT INTO parametro(id, descripcion, nombre, valor, tipo) VALUES ('1', 'Máximo de cantidad permitido modificar sin advertencia', 'producto.cantidad.warning', '3', '2');
-- ----------------------------
-- Records of direccion
-- ----------------------------
INSERT INTO direccion(id, domicilio, codigopostal, ciudad_id) VALUES ('1', '1° de Mayo 2039 - 6C', '3000', '16391');

-- ----------------------------
-- Records of empresa cliente
-- ----------------------------
INSERT INTO empresa(id, nombre, fechaInicioActividad,cuit, ingBrutos, telefono, email, tipoResponsable, prefijoFactura, numeroFacturaActual, direccion_id) 
VALUES ('1', 'Agustina Berraz Montyn', '02-08-2011', '27-28764155-4', 'No Responsable', '3424071797', 'agustinabm@hotmail.com', 'Responsable Monotributo', '1', '1', '1');

-- ----------------------------
-- Records of tipo de productos
-- ----------------------------
INSERT INTO tipo(id, descripcion) values('1','Electrodoméstico');
INSERT INTO tipo(id, descripcion) values('2','Blancos');
INSERT INTO tipo(id, descripcion) values('3','Fotografía');
INSERT INTO tipo(id, descripcion) values('4','Informática');
INSERT INTO tipo(id, descripcion) values('5','Tv y Video');
INSERT INTO tipo(id, descripcion) values('6','Regalos');

-- ----------------------------
-- Records of marca de productos
-- ----------------------------
INSERT INTO marca(id, descripcion) values('1','Philips');	 
INSERT INTO marca(id, descripcion) values('2','Samsung');	 
INSERT INTO marca(id, descripcion) values('3','M&M');	 
INSERT INTO marca(id, descripcion) values('4','Europe');	 
INSERT INTO marca(id, descripcion) values('5','LG');	 
INSERT INTO marca(id, descripcion) values('6','Fender');	 

-- ----------------------------
-- Records of productos
-- ----------------------------
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado) 
VALUES ('0', '*', 'Comodín', '0', '0', '0', '0', '0', 'false');	
	