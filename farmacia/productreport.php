<?php include('./constant/layout/head.php'); ?>
<?php include('./constant/layout/header.php'); ?>

<?php include('./constant/layout/sidebar.php'); ?>


<div class="page-wrapper">

    <div class="row page-titles">
        <div class="col-md-5 align-self-center">
            <h3 class="text-primary">Reporte Productos</h3>
        </div>
        <div class="col-md-7 align-self-center">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="javascript:void(0)">Inicio</a></li>
                <li class="breadcrumb-item active">>Reporte Productos</li>
            </ol>
        </div>
    </div>


    <div class="container-fluid">




        <div class="row">
            <div class="col-lg-8" style="    margin-left: 10%;">
                <div class="card">
                    <div class="card-title">

                    </div>
                    <div id="add-brand-messages"></div>
                    <div class="card-body">
                        <div class="input-states">
                            <form class="form-horizontal needs-validation" action="getproductreport.php" method="post" id="getOrderReportForm" novalidate>

                                <div class="form-group">
                                    <div class="row">
                                        <label class="col-sm-3 control-label">Fecha Inicio</label>
                                        <div class="col-sm-9">
                                            <input type="date" class="form-control" id="startDate" name="startDate" placeholder="Start Date" required/>
                                            <div id="error-startdate" class="invalid-feedback">
                                                La fecha inicial no puede estar vacía
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <label class="col-sm-3 control-label">Fecha Fin</label>
                                        <div class="col-sm-9">
                                            <input type="date" class="form-control" id="endDate" name="endDate" placeholder="Fecha Fin" required/>
                                            <div id="error-enddate" class="invalid-feedback">
                                                La fecha final no puede estar vacía
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <button type="submit" id="generateReportBtn" class="btn btn-primary btn-flat m-b-30 m-t-30">Generar Reporte</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>

        <script>

            const forms = document.querySelector('.needs-validation');
            forms.addEventListener('submit', (event) => {
                if (!forms.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                } else {
                const startDateInput = document.getElementById('startDate');
                const endDateInput = document.getElementById('endDate');
                const currentDate = new Date();
                const startDate = new Date(startDateInput.value);
                const endDate = new Date(endDateInput.value);

                if (startDate > endDate) {
                    document.getElementById('error-startdate').textContent = 'La fecha inicial debe ser menor o igual a la fecha final';
                    document.getElementById('error-startdate').style.display="block";
                    startDateInput.style.borderColor="red";
                    event.preventDefault();
                } else {
                    document.getElementById('error-startdate').textContent = 'La fecha inicial no puede estar vacía';
                    document.getElementById('error-startdate').style.display="none";
                    startDateInput.style.borderColor="#e7e7e7";
                }

                if (endDate > currentDate) {
                    document.getElementById('error-enddate').textContent = 'La fecha final debe ser menor o igual a la fecha actual';
                    document.getElementById('error-enddate').style.display="block";
                    endDateInput.style.borderColor="red";
                    event.preventDefault();
                } else {
                    document.getElementById('error-enddate').textContent = 'La fecha final no puede estar vacía';
                    document.getElementById('error-enddate').style.display="none";
                    endDateInput.style.borderColor="#e7e7e7";
                }

            }

                forms.classList.add('was-validated')
            });

        </script> 


        <?php include('./constant/layout/footer.php'); ?>
