using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.ServiceProcess;
using System.Text;
using System.Threading;
using System.Net;
using System.Net.Sockets;
using System.IO;
using EpsonFPHostControlX;

namespace MitnickPrinterService
{
    public partial class MitnickPrinterService : ServiceBase
    {
        private IEpsonFPHostControl epson = new EpsonFPHostControl();

        public MitnickPrinterService()
        {
            InitializeComponent();
        }
        
        private TcpListener tcpListener;

        public static ManualResetEvent tcpClientConnected = new ManualResetEvent(false);

        protected override void OnStart(string[] args)
        {
            ThreadProcess threadProcess = new ThreadProcess();
            threadProcess.tcpListener = new TcpListener(9095);
            tcpListener = threadProcess.tcpListener;

            Thread thread = new Thread(threadProcess.Process);
            thread.Start();
        }

        protected override void OnStop()
        {
            tcpListener.Stop();
        }

        protected override void OnContinue()
        {

        }

        public class ThreadProcess
        {
            public TcpListener tcpListener { get; set; }

            public void Process()
            {
                tcpListener.Start();

                tcpListener.BeginAcceptTcpClient(new AsyncCallback(DoAcceptTcpClientCallback), tcpListener);
            }
        }

        public static void DoAcceptTcpClientCallback(IAsyncResult ar)
        {
            TcpListener listener = (TcpListener)ar.AsyncState;

            TcpClient client = listener.EndAcceptTcpClient(ar);

            ThreadSocket threadSocket = new ThreadSocket();

            threadSocket.Handler = client;
            Thread thread = new Thread(threadSocket.Process);
            thread.Start();

            tcpClientConnected.Set();
        }

        public class ThreadSocket
        {
            public TcpClient Handler { get; set; }
            private StreamReader Reader { get; set; }
            private StreamWriter Writer { get; set; }

            private IEpsonFPHostControl epson = new EpsonFPHostControl();

            private string TICKET_COMMAND = (char)10 + "" + (char)1;
            private string TICKET_COMMAND_EXT = (char)0 + "" + (char)0;
            private string ITEM_COMMAND = (char)10 + "" + (char)2;
            private string ITEM_COMMAND_EXT = (char)0 + "" + (char)0;
            private string SUBTOTAL_COMMAND = (char)10 + "" + (char)3;
            private string SUBTOTAL_COMMAND_EXT = (char)0 + "" + (char)0;
            private string DISCOUNT_COMMAND = (char)10 + "" + (char)4;
            private string DISCOUNT_COMMAND_EXT = (char)0 + "" + (char)0;
            private string SURCHARGE_COMMAND = (char)10 + "" + (char)4;
            private string SURCHARGE_COMMAND_EXT = (char)0 + "" + (char)1;
            private string PAYMENT_COMMAND = (char)10 + "" + (char)5;
            private string PAYMENT_COMMAND_EXT = (char)0 + "" + (char)0;
            private string CLOSE_COMMAND = (char)10 + "" + (char)6;
            private string CLOSE_COMMAND_EXT = (char)0 + "" + (char)1;
            private string BLANK_LINE_COMMAND = (char)7 + "" + (char)1;
            private string BLANK_LINE_COMMAND_EXT = (char)0 + "" + (char)0;

            private string CIERREZ_COMMAND = (char)8 + "" + (char)1;
            private string CIERREZ_COMMAND_EXT = (char)12 + "" + (char)0;
            private string CIERREX_COMMAND = (char)8 + "" + (char)2;
            private string CIERREX_COMMAND_EXT = (char)0 + "" + (char)1;
            private string INFORME_JORNADA_COMMAND = (char)8 + "" + (char)3;
            private string INFORME_JORNADA_COMMAND_EXT = (char)0 + "" + (char)0;

            private string TICKET_FACTURA_COMMAND = (char)11 + "" + (char)1;
            private string TICKET_FACTURA_COMMAND_EXT = (char)0 + "" + (char)0;
            private string ITEM_FACTURA_COMMAND = (char)11 + "" + (char)2;
            private string ITEM_FACTURA_COMMAND_EXT = (char)0 + "" + (char)0;
            private string SUBTOTAL_FACTURA_COMMAND = (char)11 + "" + (char)3;
            private string SUBTOTAL_FACTURA_COMMAND_EXT = (char)0 + "" + (char)0;
            private string DISCOUNT_FACTURA_COMMAND = (char)11 + "" + (char)4;
            private string DISCOUNT_FACTURA_COMMAND_EXT = (char)0 + "" + (char)0;
            private string SURCHARGE_FACTURA_COMMAND = (char)11 + "" + (char)4;
            private string SURCHARGE_FACTURA_COMMAND_EXT = (char)0 + "" + (char)1;
            private string PAYMENT_FACTURA_COMMAND = (char)11 + "" + (char)5;
            private string PAYMENT_FACTURA_COMMAND_EXT = (char)0 + "" + (char)0;
            private string CLOSE_FACTURA_COMMAND = (char)11 + "" + (char)6;
            private string CLOSE_FACTURA_COMMAND_EXT = (char)0 + "" + (char)1;

            private string CONFIGURAR_COMMAND = (char)5 + "" + (char)14;
            private string CONFIGURAR_DOMICILIO_COMERCIAL_1 = (char)0 + "" + (char)0;
            private string CONFIGURAR_DOMICILIO_COMERCIAL_2 = (char)0 + "" + (char)1;
            private string CONFIGURAR_DOMICILIO_COMERCIAL_3 = (char)0 + "" + (char)2;
            private string CONFIGURAR_DOMICILIO_FISCAL_1 = (char)0 + "" + (char)3;
            private string CONFIGURAR_DOMICILIO_FISCAL_2 = (char)0 + "" + (char)4;
            private string CONFIGURAR_DOMICILIO_FISCAL_3 = (char)0 + "" + (char)5;
            private string CONFIGURAR_INGRESOS_BRUTOS_1 = (char)0 + "" + (char)6;
            private string CONFIGURAR_INGRESOS_BRUTOS_2 = (char)0 + "" + (char)7;
            private string CONFIGURAR_INGRESOS_BRUTOS_3 = (char)0 + "" + (char)8;
            private string CONFIGURAR_INICIO_ACTIVIDADES = (char)0 + "" + (char)9;

            private string INFO_TICKET_FACTURA = (char)11 + "" + (char)10;
            private string INFO_TICKET_FACTURA_EXTRA = (char)0 + "" + (char)0;
            private string CANCELAR_TICKET_FACTURA = (char)11 + "" + (char)7;
            private string CANCELAR_TICKET_FACTURA_EXTRA = (char)0 + "" + (char)0;

            private string INFO_TICKET = (char)10 + "" + (char)10;
            private string INFO_TICKET_EXTRA = (char)0 + "" + (char)0;
            private string CANCELAR_TICKET = (char)10 + "" + (char)7;
            private string CANCELAR_TICKET_EXTRA = (char)0 + "" + (char)0;

            private string INICIO_CARGA_LOGO = (char)5 + "" + (char)48;

            public void Process()
            {
                var stream = Handler.GetStream();

                NetworkStream ns = new NetworkStream(Handler.Client);
                Reader = new StreamReader(ns);
                Writer = new StreamWriter(ns);

                string line;
                while (!(line = Reader.ReadLine()).Equals("[FIN-TICKET]"))
                {
                    switch (line)
                    {
                        case "[TICKET]":
                            PrintTicket();
                            break;
                        case "[TICKET-FACTURA]":
                            PrintTicketFactura();
                            break;
                        case "[CIERRE-Z]":
                            PrintCierreZ();
                            break;
                        case "[CIERRE-X]":
                            PrintCierreZ();
                            break;
                        case "[INFORME-JORNADA]":
                            PrintInformeJornada();
                            break;
                        case "[CONFIGURACION]":
                            Configurar();
                            break;
                        case "[CARGAR-LOGO]":
                            CargarLogo();
                            break;
                        case "[INFO-TICKET-FACTURA]":
                            InfoTicketFactura();
                            break;
                        case "[CANCELAR-TICKET-FACTURA]":
                            CancelarTicketFactura();
                            break;
                        case "[INFO-TICKET]":
                            InfoTicket();
                            break;
                        case "[CANCELAR-TICKET]":
                            CancelarTicket();
                            break;
                    }
                }

                Writer.WriteLine("<FIN DE IMPRESION>");
                Writer.Flush();

                FPDelay();

                Reader.Close();
                Writer.Close();
                ns.Close();

                //Handler.Client.Shutdown(SocketShutdown.; //"Apagamos" los sockets
                Handler.Close();
                Handler.Client.Disconnect(true);

                throw new Exception();
            }

            private void CancelarTicket()
            {
                bool isOpen = Connect();

                if (isOpen)
                {
                    if (isOpen) isOpen = epson.AddDataField(CANCELAR_TICKET);
                    if (isOpen) isOpen = epson.AddDataField(CANCELAR_TICKET_EXTRA);
                    //if (isOpen) isOpen = epson.AddDataField(Reader.ReadLine()); // NUMERO DE TICKET

                    if (isOpen) isOpen = epson.SendCommand();
                    FPDelay();

                    if (epson.ReturnCode != 0) SendErrorMessage();
                    else
                    {
                        Writer.WriteLine(epson.GetExtraField(1));
                        Writer.Flush();
                    }
                }
            }

            private void InfoTicket()
            {
                //bool isOpen = Connect();
                bool isOpen = true;

                if (isOpen)
                {
                    if (isOpen) isOpen = epson.AddDataField(INFO_TICKET);
                    if (isOpen) isOpen = epson.AddDataField(INFO_TICKET_EXTRA);

                    if (isOpen) isOpen = epson.SendCommand();
                    FPDelay();

                    if (epson.ReturnCode != 0) SendErrorMessage();

                    for (int i = 1; i <= 14; i++)
                    {
                        Writer.WriteLine(i + ":" + epson.GetExtraField(i));
                        Writer.Flush();
                    }
                }
            }

            private void CancelarTicketFactura()
            {
                bool isOpen = Connect();

                if (isOpen)
                {
                    if (isOpen) isOpen = epson.AddDataField(CANCELAR_TICKET_FACTURA);
                    if (isOpen) isOpen = epson.AddDataField(CANCELAR_TICKET_FACTURA_EXTRA);

                    if (isOpen) isOpen = epson.SendCommand();
                    FPDelay();

                    if (epson.ReturnCode != 0) SendErrorMessage();
                    else
                    {
                        Writer.WriteLine(epson.GetExtraField(1));
                        Writer.Flush();
                        Writer.WriteLine(epson.GetExtraField(2));
                        Writer.Flush();
                    }
                }
            }

            private void InfoTicketFactura()
            {
                bool isOpen = Connect();

                if (isOpen)
                {
                    if (isOpen) isOpen = epson.AddDataField(INFO_TICKET_FACTURA);
                    if (isOpen) isOpen = epson.AddDataField(INFO_TICKET_FACTURA_EXTRA);

                    if (isOpen) isOpen = epson.SendCommand();
                    FPDelay();

                    if (epson.ReturnCode != 0) SendErrorMessage();

                    for(int i = 1; i <= 19; i++) {
                        Writer.WriteLine(i +":" + epson.GetExtraField(i));
                        Writer.Flush();
                    }
                }
            }

            private void CargarLogo()
            {
                bool isOpen = Connect();

                if (isOpen)
                {
                    if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_COMMAND);

                    if (isOpen) isOpen = epson.SendCommand();
                    FPDelay();

                    if (epson.ReturnCode != 0) SendErrorMessage();

                    //epson.AddDataField(
                }
            }

            private void Configurar()
            {
                bool isOpen = Connect();

                if (isOpen)
                {
                    string line = Reader.ReadLine();

                    if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_COMMAND);

                    switch (line)
                    {
                        case "[DOMICILIO-COMERCIAL-1]":
                            line = Reader.ReadLine();
                            if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_DOMICILIO_COMERCIAL_1);
                            if (isOpen) isOpen = epson.AddDataField(line);
                            break;
                        case "[DOMICILIO-COMERCIAL-2]":
                            line = Reader.ReadLine();
                            if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_DOMICILIO_COMERCIAL_2);
                            if (isOpen) isOpen = epson.AddDataField(line);
                            break;
                        case "[DOMICILIO-COMERCIAL-3]":
                            line = Reader.ReadLine();
                            if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_DOMICILIO_COMERCIAL_3);
                            if (isOpen) isOpen = epson.AddDataField(line);
                            break;
                        case "[DOMICILIO-FISCAL-1]":
                            line = Reader.ReadLine();
                            if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_DOMICILIO_FISCAL_1);
                            if (isOpen) isOpen = epson.AddDataField(line);
                            break;
                        case "[DOMICILIO-FISCAL-2]":
                            line = Reader.ReadLine();
                            if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_DOMICILIO_FISCAL_2);
                            if (isOpen) isOpen = epson.AddDataField(line);
                            break;
                        case "[DOMICILIO-FISCAL-3]":
                            line = Reader.ReadLine();
                            if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_DOMICILIO_FISCAL_3);
                            if (isOpen) isOpen = epson.AddDataField(line);
                            break;
                        case "[INGRESOS-BRUTOS-1]":
                            line = Reader.ReadLine();
                            if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_INGRESOS_BRUTOS_1);
                            if (isOpen) isOpen = epson.AddDataField(line);
                            break;
                        case "[INGRESOS-BRUTOS-2]":
                            line = Reader.ReadLine();
                            if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_INGRESOS_BRUTOS_2);
                            if (isOpen) isOpen = epson.AddDataField(line);
                            break;
                        case "[INGRESOS-BRUTOS-3]":
                            line = Reader.ReadLine();
                            if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_INGRESOS_BRUTOS_3);
                            if (isOpen) isOpen = epson.AddDataField(line);
                            break;
                        case "[INGRESO-ACTIVIDADES]":
                            line = Reader.ReadLine();
                            if (isOpen) isOpen = epson.AddDataField(CONFIGURAR_INICIO_ACTIVIDADES);
                            if (isOpen) isOpen = epson.AddDataField(line);
                            break;
                    }

                    if (isOpen) isOpen = epson.SendCommand();
                    FPDelay();

                    if (epson.ReturnCode != 0) SendErrorMessage();
                }
            }

            private void PrintInformeJornada()
            {
                bool isOpen = Connect();

                if (isOpen)
                {
                    bool isOk = true;
                    if (isOk) isOk = epson.AddDataField(INFORME_JORNADA_COMMAND);
                    if (isOk) isOk = epson.AddDataField(INFORME_JORNADA_COMMAND_EXT);
                    if (isOk) isOk = epson.SendCommand();
                    FPDelay();

                    if (epson.ReturnCode != 0) SendErrorMessage();
                }
                else
                {
                    SendConnectionError();
                }
            }

            private void PrintTicketFactura()
            {
                bool isOpen = Connect();

                if (isOpen)
                {
                    if (isOpen) isOpen = epson.AddDataField(TICKET_FACTURA_COMMAND);
                    if (isOpen) isOpen = epson.AddDataField(TICKET_FACTURA_COMMAND_EXT);
                    
                    string line;
                    while (!(line = Reader.ReadLine()).Equals("[FIN-TICKET]"))
                    {
                        switch (line)
                        {
                            case "[DATOS-COMPRADOR]":
                                PrintDatosComprador();
                                break;
                            case "[ITEM]":
                                PrintItemInvoice();
                                break;
                            case "[SUBTOTAL]":
                                PrintSubTotalInvoice();
                                break;
                            case "[DISCOUNT]":
                                PrintDiscountInvoice();
                                break;
                            case "[SURCHARGE]":
                                PrintSurchargeInvoice();
                                break;
                            case "[PAYMENT]":
                                PrintPaymentInvoice();
                                break;
                            case "[BLANK-LINE]":
                                PrintBlankLine();
                                break;
                            case "[INFO-TICKET-FACTURA]":
                                InfoTicketFactura();
                                break;
                            case "[CANCELAR-TICKET-FACTURA]":
                                CancelarTicketFactura();
                                break;
                        }
                    }

                    PrintCloseInvoice();
                }
                else
                {
                    SendConnectionError();
                }
            }

            private void PrintDatosComprador()
            {
                bool isOk = true;

                string line;
                while (!(line = Reader.ReadLine()).Equals("[FIN-DATOS-COMPRADOR]"))
                {
                    switch (line)
                    {
                        case "[NOMBRE-COMPRADOR]":
                        case "[DIRECCION-COMPRADOR]":
                        case "[TIPO-IVA-COMPRADOR]":
                        case "[NUMERO-DOCUMENTO-COMPRADOR]":
                        case "[TIPO-DOCUMENTO-COMPRADOR]":
                        case "[LINEA-REMITOS-ASOCIADOS]":
                            if (isOk) isOk = epson.AddDataField(Reader.ReadLine());
                            break;
                    }
                }
                if (isOk) isOk = epson.AddDataField("");

                if (isOk) isOk = epson.SendCommand();
                FPDelay();

                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintCierreZ()
            {
                bool isOpen = Connect();

                if (isOpen)
                {
                    bool isOk = true;
                    if (isOk) isOk = epson.AddDataField(CIERREZ_COMMAND);
                    if (isOk) isOk = epson.AddDataField(CIERREZ_COMMAND_EXT);
                    if (isOk) isOk = epson.SendCommand();
                    FPDelay();

                    if (epson.ReturnCode != 0) SendErrorMessage();
                }
                else
                {
                    SendConnectionError();
                }
            }

            private void PrintCierreX()
            {
                bool isOk = Connect();
                if (isOk)
                {
                    if (isOk) isOk = epson.AddDataField(CIERREX_COMMAND);
                    if (isOk) isOk = epson.AddDataField(CIERREX_COMMAND_EXT);
                    if (isOk) isOk = epson.SendCommand();
                    FPDelay();

                    if (epson.ReturnCode != 0) SendErrorMessage();
                }
                else
                {
                    SendConnectionError();
                }
                
            }

            private void PrintTicket()
            {
                bool isOpen = Connect();

                if (isOpen)
                {
                    if (isOpen) isOpen = epson.AddDataField(TICKET_COMMAND);
                    if (isOpen) isOpen = epson.AddDataField(TICKET_COMMAND_EXT);
                    if (isOpen) isOpen = epson.SendCommand();
                    FPDelay();

                    if (epson.ReturnCode != 0) SendErrorMessage();

                    string line;
                    while (!(line = Reader.ReadLine()).Equals("[FIN-TICKET]"))
                    {
                        switch (line)
                        {
                            case "[ITEM]":
                                PrintItem();
                                break;
                            case "[SUBTOTAL]":
                                PrintSubTotal();
                                break;
                            case "[DISCOUNT]":
                                PrintDiscount();
                                break;
                            case "[SURCHARGE]":
                                PrintSurcharge();
                                break;
                            case "[PAYMENT]":
                                PrintPayment();
                                break;
                            case "[BLANK-LINE]":
                                PrintBlankLine();
                                break;
                            case "[INFO-TICKET]":
                                InfoTicket();
                                break;
                            case "[CANCELAR-TICKET]":
                                CancelarTicket();
                                break;
                        }
                    }

                    PrintClose();
                }
                else
                {
                    SendConnectionError();
                }
            }

            private void PrintDiscount()
            {
                bool isOk = true;
                if (isOk) isOk = epson.AddDataField(DISCOUNT_COMMAND);
                if (isOk) isOk = epson.AddDataField(DISCOUNT_COMMAND_EXT);

                if (isOk) isOk = epson.AddDataField(Reader.ReadLine());

                string discount = Reader.ReadLine();
                discount = discount.Replace(",", "").Replace(".", "");
                if (isOk) isOk = epson.AddDataField(discount);

                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintSurcharge()
            {
                bool isOk = true;
                if (isOk) isOk = epson.AddDataField(SURCHARGE_COMMAND);
                if (isOk) isOk = epson.AddDataField(SURCHARGE_COMMAND);

                if (isOk) isOk = epson.AddDataField(Reader.ReadLine());

                string discount = Reader.ReadLine();
                discount = discount.Replace(",", "").Replace(".", "");
                if (isOk) isOk = epson.AddDataField(discount);

                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintDiscountInvoice()
            {
                bool isOk = true;
                if (isOk) isOk = epson.AddDataField(DISCOUNT_FACTURA_COMMAND);
                if (isOk) isOk = epson.AddDataField(DISCOUNT_FACTURA_COMMAND_EXT);

                if (isOk) isOk = epson.AddDataField(Reader.ReadLine());

                string discount = Reader.ReadLine();
                discount = discount.Replace(",", "").Replace(".", "");
                if (isOk) isOk = epson.AddDataField(discount);

                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintSurchargeInvoice()
            {
                bool isOk = true;
                if (isOk) isOk = epson.AddDataField(SURCHARGE_FACTURA_COMMAND);
                if (isOk) isOk = epson.AddDataField(SURCHARGE_FACTURA_COMMAND_EXT);

                if (isOk) isOk = epson.AddDataField(Reader.ReadLine());

                string discount = Reader.ReadLine();
                discount = discount.Replace(",", "").Replace(".", "");
                if (isOk) isOk = epson.AddDataField(discount);

                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void SendConnectionError()
            {
                Writer.WriteLine("[ERROR]");
                Writer.WriteLine("No se pudo conectar a la impresora");
                Writer.Flush();
            }

            private bool Connect()
            {
                epson.ClosePort();

                epson.CommPort = TxCommPort.USB;
                epson.BaudRate = TxBaudRate.br9600;
                epson.ProtocolType = TxProtocolType.protocol_Extended;

                return epson.OpenPort();
            }

            private void PrintBlankLine()
            {
                bool isOk = true;
                if (isOk) isOk = epson.AddDataField(BLANK_LINE_COMMAND);
                if (isOk) isOk = epson.AddDataField(BLANK_LINE_COMMAND_EXT);

                if (isOk) isOk = epson.AddDataField("1");
                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintClose()
            {
                bool isOk = true;
                if (isOk) isOk = epson.AddDataField(CLOSE_COMMAND);
                if (isOk) isOk = epson.AddDataField(CLOSE_COMMAND_EXT);

                string line;
                string data = "";
                int cola = 0;

                while (!(line = Reader.ReadLine()).Equals("[FIN-COLA-TICKET]"))
                {
                    switch (line)
                    {
                        case "[CLOSE-COLA]":
                            data = Reader.ReadLine();
                            cola++;
                            if (isOk) isOk = epson.AddDataField(cola + "");
                            if (isOk) isOk = epson.AddDataField(data);
                            break;
                        case "[FIN-COLA]":
                            while (cola < 3)
                            {
                                cola++;
                                if (isOk) isOk = epson.AddDataField(cola + "");
                                if (isOk) isOk = epson.AddDataField("");
                            }
                            break;
                    }
                }

                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintPayment()
            {
                bool isOk = true;

                if (isOk) isOk = epson.AddDataField(PAYMENT_COMMAND);
                if (isOk) isOk = epson.AddDataField(PAYMENT_COMMAND_EXT);

                string line;
                string data = "";
                int descLine = 0;

                while (!(line = Reader.ReadLine()).Equals("[FIN-PAGO]"))
                {
                    switch (line)
                    {
                        case "[PAGO-MONTO]":
                            data = Reader.ReadLine();
                            string newData = data.Replace(",", "").Replace(".", "");
                            if (isOk) isOk = epson.AddDataField(newData);
                            break;
                        case "[PAGO-DESCRIPTION]":
                            data = Reader.ReadLine();
                            descLine++;
                            if (isOk) isOk = epson.AddDataField(data);
                            break;
                        case "[FIN-PAGO-DESCRIPTION]":
                            while (descLine < 2)
                            {
                                descLine++;
                                if (isOk) isOk = epson.AddDataField("");
                            }
                            break;
                    }
                }

                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintSubTotal()
            {
                bool isOk = true;
                if (isOk) isOk = epson.AddDataField(SUBTOTAL_COMMAND);
                if (isOk) isOk = epson.AddDataField(SUBTOTAL_COMMAND_EXT);
                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintItem()
            {
                bool isOk = true;

                if (isOk) isOk = epson.AddDataField(ITEM_COMMAND);
                if (isOk) isOk = epson.AddDataField(ITEM_COMMAND_EXT);

                string line;
                string data = "";
                int descLine = 0;

                while (!(line = Reader.ReadLine()).Equals("[FIN-ITEM]"))
                {
                    
                    switch (line)
                    {
                        case "[ITEM-CANTIDAD]":
                            data = Reader.ReadLine();
                            string newData = data.Replace(",", "").Replace(".", "") + "0000";
                            if (isOk) isOk = epson.AddDataField(newData);
                            break;
                        case "[ITEM-PRECIO]":
                        case "[ITEM-IVA]":
                            data = Reader.ReadLine();
                            string newData2 = data.Replace(",", "").Replace(".", "") + "00";
                            if (isOk) isOk = epson.AddDataField(newData2);
                            break;
                        case "[ITEM-DESCRIPTION]":
                            data = Reader.ReadLine();
                            descLine++;
                            if (isOk) isOk = epson.AddDataField(data);
                            break;
                        case "[FIN-ITEM-DESCRIPTION]":
                            while (descLine < 5)
                            {
                                descLine++;
                                if (isOk) isOk = epson.AddDataField("");
                            }
                            break;
                    }
                }

                if (isOk) isOk = epson.AddDataField("");
                if (isOk) isOk = epson.AddDataField("");

                if (isOk) isOk = epson.SendCommand();
                FPDelay();

                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintCloseInvoice()
            {
                bool isOk = true;
                if (isOk) isOk = epson.AddDataField(CLOSE_FACTURA_COMMAND);
                if (isOk) isOk = epson.AddDataField(CLOSE_FACTURA_COMMAND_EXT);

                string line;
                string data = "";
                int cola = 0;

                while (!(line = Reader.ReadLine()).Equals("[FIN-COLA-TICKET]"))
                {
                    switch (line)
                    {
                        case "[CLOSE-COLA]":
                            data = Reader.ReadLine();
                            cola++;
                            if (isOk) isOk = epson.AddDataField(cola + "");
                            if (isOk) isOk = epson.AddDataField(data);
                            break;
                        case "[FIN-COLA]":
                            while (cola < 3)
                            {
                                cola++;
                                if (isOk) isOk = epson.AddDataField(cola + "");
                                if (isOk) isOk = epson.AddDataField("");
                            }
                            break;
                    }
                }

                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintPaymentInvoice()
            {
                bool isOk = true;

                if (isOk) isOk = epson.AddDataField(PAYMENT_FACTURA_COMMAND);
                if (isOk) isOk = epson.AddDataField(PAYMENT_FACTURA_COMMAND_EXT);

                string line;
                string data = "";
                int descLine = 0;

                while (!(line = Reader.ReadLine()).Equals("[FIN-PAGO]"))
                {
                    switch (line)
                    {
                        case "[PAGO-MONTO]":
                            data = Reader.ReadLine();
                            string newData = data.Replace(",", "").Replace(".", "");
                            if (isOk) isOk = epson.AddDataField(newData);
                            break;
                        case "[PAGO-DESCRIPTION]":
                            data = Reader.ReadLine();
                            descLine++;
                            if (isOk) isOk = epson.AddDataField(data);
                            break;
                        case "[FIN-PAGO-DESCRIPTION]":
                            while (descLine < 2)
                            {
                                descLine++;
                                if (isOk) isOk = epson.AddDataField("");
                            }
                            break;
                    }
                }

                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintSubTotalInvoice()
            {
                bool isOk = true;
                if (isOk) isOk = epson.AddDataField(SUBTOTAL_FACTURA_COMMAND);
                if (isOk) isOk = epson.AddDataField(SUBTOTAL_FACTURA_COMMAND_EXT);
                if (isOk) isOk = epson.SendCommand();
                FPDelay();
                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            private void PrintItemInvoice()
            {
                bool isOk = true;

                if (isOk) isOk = epson.AddDataField(ITEM_FACTURA_COMMAND);
                if (isOk) isOk = epson.AddDataField(ITEM_FACTURA_COMMAND_EXT);

                string line;
                string data = "";
                int descLine = 0;

                while (!(line = Reader.ReadLine()).Equals("[FIN-ITEM]"))
                {

                    switch (line)
                    {
                        case "[ITEM-CANTIDAD]":
                            data = Reader.ReadLine();
                            string newData = data.Replace(",", "").Replace(".", "") + "0000";
                            if (isOk) isOk = epson.AddDataField(newData);
                            break;
                        case "[ITEM-PRECIO]":
                        case "[ITEM-IVA]":
                            data = Reader.ReadLine();
                            string newData2 = data.Replace(",", "").Replace(".", "") + "00";
                            if (isOk) isOk = epson.AddDataField(newData2);
                            break;
                        case "[ITEM-DESCRIPTION]":
                            data = Reader.ReadLine();
                            descLine++;
                            if (isOk) isOk = epson.AddDataField(data);
                            break;
                        case "[FIN-ITEM-DESCRIPTION]":
                            while (descLine < 5)
                            {
                                descLine++;
                                if (isOk) isOk = epson.AddDataField("");
                            }
                            break;
                    }
                }

                if (isOk) isOk = epson.AddDataField("");
                if (isOk) isOk = epson.AddDataField("");

                if (isOk) isOk = epson.SendCommand();
                FPDelay();

                if (epson.ReturnCode != 0) SendErrorMessage();
            }

            public void FPDelay() {
                var start1 = DateTime.Now;

                while(epson.State.Equals(TxFiscalState.EFP_S_Busy)) {
                    while(DateTime.Now < start1.AddMilliseconds(125)) {
                        Thread.Sleep(150);
                        if(start1 > DateTime.Now)
                            break;
                    }
                }
            }

            public void SendErrorMessage() {
                Writer.WriteLine("[ERROR]");
                Writer.WriteLine("Código de Retorno: " + String.Format("{0:X}", epson.ReturnCode) + " " + epson.LastError);
                Writer.Flush();
            }
        }
              
    }
}
