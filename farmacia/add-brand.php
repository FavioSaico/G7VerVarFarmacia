<?php include('./constant/layout/head.php'); ?>
<?php include('./constant/layout/header.php'); ?>

<?php include('./constant/layout/sidebar.php'); ?>


<div class="page-wrapper">

    <div class="row page-titles">
        <div class="col-md-5 align-self-center">
            <h3 class="text-primary">Agregar Proveedor</h3>
        </div>
        <div class="col-md-7 align-self-center">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="javascript:void(0)">Inicio</a></li>
                <li class="breadcrumb-item active">Agregrar Proveedor</li>
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
                            <form class="form-horizontal needs-validation" method="POST" id="submitBrandForm" action="php_action/createBrand.php" enctype="multipart/form-data" novalidate>

                                <input type="hidden" name="currnt_date" class="form-control">

                                <div class="form-group">
                                    <div class="row">
                                        <label class="col-sm-3 control-label">Nombre Proveedor</label>
                                        <div class="col-sm-9">
                                            <input type="text" class="form-control" id="brandName" placeholder="Proveedor" name="brandName" required/>
                                            <div id="error-brand" class="invalid-feedback">
                                                Escriba el nombre del proveedor
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <label class="col-sm-3 control-label">Estado</label>
                                        <div class="col-sm-9">
                                            <select class="form-control" id="brandStatus" name="brandStatus" required>
                                                <option value="">~~Seleccionar~~</option>
                                                <option value="1">Disponible</option>
                                                <option value="2">No disponible</option>
                                            </select>
                                            <div id="error-status" class="invalid-feedback">
                                                Debe seleccionar un estado
                                            </div>
                                        </div>
                                    </div>
                                </div>


                                <button type="submit" name="create" id="createBrandBtn" class="btn btn-primary btn-flat m-b-30 m-t-30">Enviar</button>
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
                    const brandNameInput = document.getElementById('brandName');
                    const brandNameValue = brandNameInput.value;

                    if(brandNameValue.length > 50){
                        document.getElementById('error-brand').textContent = 'El nombre debe tener menos de 50 caracteres';
                        brandNameInput.style.borderColor="red";
                        document.getElementById('error-brand').style.display="block";
                        event.preventDefault();
                    }
                }

                forms.classList.add('was-validated')
            });

        </script> 



        <?php include('./constant/layout/footer.php'); ?>
