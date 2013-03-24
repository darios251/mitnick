-- ----------------------------
-- Records of parametro
-- ----------------------------
INSERT INTO parametro(id, descripcion, nombre, valor, tipo) VALUES ('1', 'Máximo de cantidad permitido modificar sin advertencia', 'producto.cantidad.warning', '-3', '2');
-- ----------------------------
-- Records of direccion
-- ----------------------------
INSERT INTO direccion(id, domicilio, codigopostal, ciudad_id) VALUES ('1', 'Av. 7 de Marzo 1808', '3016', '1864');

-- ----------------------------
-- Records of empresa cliente
-- ----------------------------
INSERT INTO empresa(id, nombre, cuit, nmIngresosBrutos, telefono, email, tipoResponsable, prefijoFactura, numeroFacturaActual, direccion_id) 
VALUES ('1', 'SUSMANN LIVIO VICTOR JUAN', '20-10338232-8', '12312312312', '3424743704', 'susmannlivio@hotmail.com', 'Responsable Inscripto', '0001', '00000001', '1');

-- ----------------------------
-- Records of tipo de productos
-- ----------------------------
INSERT INTO tipo(id, descripcion) values('1','Pantalón');
INSERT INTO tipo(id, descripcion) values('2','Saco');
INSERT INTO tipo(id, descripcion) values('3','Ambo');
INSERT INTO tipo(id, descripcion) values('4','Gaban');
INSERT INTO tipo(id, descripcion) values('5','Campera');
INSERT INTO tipo(id, descripcion) values('6','Camisa');
INSERT INTO tipo(id, descripcion) values('7','Pullover');
INSERT INTO tipo(id, descripcion) values('8','Remera Polo');
INSERT INTO tipo(id, descripcion) values('9','Remera');
INSERT INTO tipo(id, descripcion) values('10','Corbata');
INSERT INTO tipo(id, descripcion) values('11','Pantalon (Corte Jean)');
INSERT INTO tipo(id, descripcion) values('12','Cinto');
INSERT INTO tipo(id, descripcion) values('13','Buzo');
INSERT INTO tipo(id, descripcion) values('14','Medias');
INSERT INTO tipo(id, descripcion) values('15','Boxer');
INSERT INTO tipo(id, descripcion) values('16','Camiseta');
INSERT INTO tipo(id, descripcion) values('17','Perfume');
INSERT INTO tipo(id, descripcion) values('18','Short');
INSERT INTO tipo(id, descripcion) values('19','Pijama');
INSERT INTO tipo(id, descripcion) values('20','Panuelo/Chalina/Bufanda');
INSERT INTO tipo(id, descripcion) values('21','Calzado');
INSERT INTO tipo(id, descripcion) values('22','Bermuda');

-- ----------------------------
-- Records of marca de productos
-- ----------------------------
INSERT INTO marca(id, descripcion) values('1','Tannery');	 
INSERT INTO marca(id, descripcion) values('2','Levis');	 
INSERT INTO marca(id, descripcion) values('3','Huapi');	 
INSERT INTO marca(id, descripcion) values('4','Wrangler');	 
INSERT INTO marca(id, descripcion) values('5','Christian Dior');	 
INSERT INTO marca(id, descripcion) values('6','Cacharel');	 
INSERT INTO marca(id, descripcion) values('7','Perramus');	 
INSERT INTO marca(id, descripcion) values('8','Roland Cotton');	 
INSERT INTO marca(id, descripcion) values('9','Stylo');	 
INSERT INTO marca(id, descripcion) values('10','Rochas');	 
INSERT INTO marca(id, descripcion) values('11','Walter House (WR)');	 
INSERT INTO marca(id, descripcion) values('12','Ocio');	 

-- ----------------------------
-- Records of medios de pago
-- ----------------------------
INSERT INTO producto(id, codigo,descripcion, stock, stockminimo, stockcompra, precioventa, iva, eliminado) 
VALUES ('0', '*', 'Comodín', '0', '0', '0', '0', '0', 'false');	
	