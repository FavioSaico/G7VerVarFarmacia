<?php include('./constant/layout/head.php'); ?>
<?php include('./constant/layout/header.php'); ?>

<?php include('./constant/layout/sidebar.php'); ?>

<?php
// Verificar si se ha enviado un archivo
if(isset($_FILES['Medicine']) && $_FILES['Medicine']['error'] === UPLOAD_ERR_OK) {
    $maxFileSize = 1048576; // Tamaño máximo permitido en bytes (1 MB en este ejemplo)

    // Verificar el tamaño del archivo
    if($_FILES['Medicine']['size'] > $maxFileSize) {
        echo "El tamaño del archivo es demasiado grande. Por favor, seleccione un archivo más pequeño.";
        // Puedes redirigir al usuario a la página anterior o mostrar un mensaje de error, según tus necesidades.
        exit;
    }

    // Resto de tu código para procesar la imagen...
}
?>

<div class="page-wrapper">

    <div class="row page-titles">
        <div class="col-md-5 align-self-center">
            <h3 class="text-primary">Agregar Medicina</h3>
        </div>
        <div class="col-md-7 align-self-center">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="javascript:void(0)">Inicio</a></li>
                <li class="breadcrumb-item active">Agregar Medicina</li>
            </ol>
        </div>
    </div>


    <div class="container-fluid">

        <div class="row">
            <div class="col-lg-10 mx-auto">
                <div class="card">
                    <div class="card-title">

                    </div>
                    <div id="add-brand-messages"></div>
                    <div class="card-body">
                        <div class="input-states">
                            <form class="row needs-validation" method="POST" id="submitProductForm" action="php_action/createProduct.php" method="POST" enctype="multipart/form-data" novalidate>

                                <input type="hidden" name="currnt_date" class="form-control">

                                <div class="form-group col-md-6">
                                    <label class="control-label">Imagen Medicina:</label>
                                    <div id="kv-avatar-errors-1" class="center-block" style="display:none;"></div>
                                    <div class="kv-avatar center-block">
                                        <input type="file" class="form-control" id="MedicineImage" placeholder="Nombre Medicina" name="Medicine" class="file-loading" style="width:auto;" accept=".jpg, .jpeg, .png" required>
                                        <div id="missing-image" class="invalid-feedback">
                                            Selecciona una imagen.
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="ontrol-label">Nombre Medicina</label>
                                    <input type="text" class="form-control" id="productName" placeholder="Nombre Medicina" name="productName" autocomplete="off" required="" />
                                    <div id="missing-name" class="invalid-feedback">
                                        Completa este campo
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Cantidad</label>
                                    <input type="text" class="form-control" id="quantity" placeholder="Cantidad" name="quantity" autocomplete="off" required="" pattern="^[0-9]+$" />
                                    <div id="missing-quantity" class="invalid-feedback">
                                        Completa este campo
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Cantidad Por Unidad</label>
                                    <input type="text" class="form-control" id="rate" placeholder="CPA" name="rate" autocomplete="off" required="" pattern="^[0-9]+$" />
                                    <div id="missing-rate" class="invalid-feedback">
                                        Completa este campo
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Precio</label>
                                    <input type="number" class="form-control" id="price" placeholder="Precio" name="price" autocomplete="off" required="" pattern="^[0-9]+$" min="1"/>
                                    <div id="missing-price" class="invalid-feedback">
                                        Completa este campo
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">PRM</label>
                                    <input type="text" class="form-control" id="mrp" placeholder="PRM" name="mrp" autocomplete="off" required pattern="^[0-9]+$" />
                                    <div id="missing-mrp" class="invalid-feedback">
                                        Completa este campo
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">N° de Lote</label>
                                    <input type="text" class="form-control" id="Batch No" placeholder="Batch No" name="bno" autocomplete="off" required="" pattern="^[Aa-Zz]+$" />
                                    <div id="missing-bno" class="invalid-feedback">
                                        Completa este campo
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Fecha Expiración</label>
                                    <?php
                                    //Declarando la variable fechaActual que almacena la fecha del día de hoy 
                                    $fechaActual = date('Y-m-d');
                                    ?>
                                    <input type="date" class="form-control" id="expdate" placeholder="Expiry Date" name="expdate" autocomplete="off" required="" min="<?= $fechaActual ?>" max="2030-12-31"/>
                                    <div id="missing-expdate" class="invalid-feedback">
                                        Completa este campo
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Nombre Proveedor</label>
                                    <select class="form-control" id="brandName" name="brandName" required>
                                        <option value="" disabled selected>~~Seleccionar~~</option>
                                        <?php
                                        $sql = "SELECT brand_id, brand_name, brand_active, brand_status FROM brands WHERE brand_status = 1 AND brand_active = 1";
                                        $result = $connect->query($sql);

                                        while ($row = $result->fetch_array()) {
                                            echo "<option value='" . $row[0] . "'>" . $row[1] . "</option>";
                                        } // while
                                        ?>
                                    </select>
                                    <div id="missing-brandName" class="invalid-feedback">
                                        Selecciona un elemento de la lista
                                    </div>
                                </div>
                                <div class="form-group col-md-6">

                                    <label class="control-label">Nombre Categoría</label>
                                    <select type="text" class="form-control" id="categoryName" name="categoryName" required>
                                        <option value="" disabled selected>~~Seleccionar~~</option>
                                        <?php
                                        $sql = "SELECT categories_id, categories_name, categories_active, categories_status FROM categories WHERE categories_status = 1 AND categories_active = 1";
                                        $result = $connect->query($sql);

                                        while ($row = $result->fetch_array()) {
                                            echo "<option value='" . $row[0] . "'>" . $row[1] . "</option>";
                                        } // while

                                        ?>
                                    </select>
                                    <div id="missing-categoryName" class="invalid-feedback">
                                        Selecciona un elemento de la lista
                                    </div>
                                </div>
                                <div class="form-group col-md-6">
                                    <label class="control-label">Estado</label>
                                    <select class="form-control" id="productStatus" name="productStatus" required>
                                        <option value="" disabled selected>~~Seleccionar~~</option>
                                        <option value="1">Disponible</option>
                                        <option value="2">No disponible</option>
                                    </select>
                                    <div id="missing-productStatus" class="invalid-feedback">
                                        Selecciona un elemento de la lista
                                    </div>
                                </div>
                                
                                <div class="col-md-12 mx-auto d-flex justify-content-center">
                                    <button type="submit" name="create" id="createProductBtn" class="btn btn-primary btn-flat m-b-30 m-t-30">Enviar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

        </div>



        <?php include('./constant/layout/footer.php'); ?>

        <script>
        document.getElementById('MedicineImage').addEventListener('change', function() {
            const file = this.files[0];
            const maxSizeInBytes = 5 * 1024 * 1024; // 5 MB
            const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;  // Extensiones permitidas: jpg, jpeg, png

            if (!allowedExtensions.test(file.name)) {
                alert('Solo se permiten archivos con extensiones .jpg, .jpeg o .png.');
                this.value = ''; // Limpiar el campo de carga para que el usuario pueda elegir otro archivo
                return;
            }

            if (file.size > maxSizeInBytes) {
                alert('El tamaño de la imagen es demasiado grande. Por favor, elige una imagen más pequeña.');
                this.value = ''; // Limpiar el campo de carga para que el usuario pueda elegir otro archivo
            }
        });

        // validación del formulario usando boostrap
        /*const forms = document.querySelector('.needs-validation');
        forms.addEventListener('submit', (event) => {
            if (!forms.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            }

            forms.classList.add('was-validated')
        });*/


        function validateForm(event) {
            const allowedCharacters = /^[A-Za-z0-9\s]+$/;
            const minLength = 3;
            const maxLength = 50;

            const imageFile = document.getElementById('MedicineImage').files[0];
            const productName = document.getElementById('productName').value;
            const quantity = document.getElementById('quantity').value;
            const unitQuantity = document.getElementById('rate').value;
            const price = document.getElementById('price').value;
            const prm = document.getElementById('mrp').value;
            const batchNo = document.getElementById('Batch No').value;
            const expdate = document.getElementById('expdate').value; // '2023-11-02' o ''
            const brandName = document.getElementById('brandName').value;
            const categoryName = document.getElementById('categoryName').value; // '1'
            const productStatus = document.getElementById('productStatus').value;

            if (!this.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
                //return false;
            }
            
            // Validando nombre del producto
            if (productName.length < minLength && productName.length != 0) {
                alert('El nombre de la medicina debe tener al menos ' + minLength + ' caracteres.');
                event.preventDefault(); // Evitar el envío del formulario
                return false;
            } else if (productName.length > maxLength) {
                alert('El nombre de la medicina no puede tener más de ' + maxLength + ' caracteres.');
                event.preventDefault(); // Evitar el envío del formulario
                return false;
            }

            if (!allowedCharacters.test(productName) && productName.length != 0) {
                alert('El nombre de la medicina solo puede contener letras, números y espacios.');
                event.preventDefault(); // Evitar el envío del formulario
                return false;
            }

            // Cantidad
            if(quantity < 1 && quantity !=''){
                alert('El valor debe ser superior o igual a 1');
                event.preventDefault();
                return false;
            }else if(quantity > 999){
                alert('El valor debe ser inferior o igual a 999');
                event.preventDefault();
                return false;
            }

            // unitQuantity
            if(unitQuantity < 1 && unitQuantity !=''){
                alert('El valor debe ser superior o igual a 1');
                event.preventDefault();
                return false;
            }else if(unitQuantity  > 999){
                alert('El valor debe ser inferior o igual a 999');
                event.preventDefault();
                return false;
            }

            // price
            if(price < 1 && price !=''){
                alert('El valor debe ser superior o igual a 1');
                event.preventDefault();
                return false;
            }else if(price  > 999){
                alert('El valor debe ser inferior o igual a 999');
                event.preventDefault();
                return false;
            }

            // prm
            if(prm < 1 && prm !=''){
                alert('El valor debe ser superior o igual a 1');
                event.preventDefault();
                return false;
            }else if(prm  > 500){
                alert('El valor debe ser inferior o igual a 500');
                event.preventDefault();
                return false;
            }

            // batchNo
            if(batchNo < 100000 && batchNo !=''){
                alert('El valor debe ser superior o igual a 100000');
                event.preventDefault();
                return false;
            }else if(batchNo  > 999999){
                alert('El valor debe ser inferior o igual a 999999');
                event.preventDefault();
                return false;
            }

            // expdate
            if (expdate !=''){
                let hoy = new Date(Date.now());
                hoy = new Date(hoy.getFullYear(),hoy.getMonth(),hoy.getDate());
                let fecha = new Date(expdate);
                fecha = new Date(fecha.getFullYear(),fecha.getMonth(),fecha.getDate()+1);
                
                if(fecha-hoy < 0 && fecha !=''){
                    alert('El valor debe ser ' + hoy.toLocaleDateString()+ ' (Fecha Actual) o superior');
                    event.preventDefault();
                    return false;
                }else if(fecha > new Date('2030-12-31')){
                    alert('El valor debe ser 31/12/2030 o anterior');
                    event.preventDefault();
                    return false;
                }
            }
            this.classList.add('was-validated')
            return true;
        }

        document.getElementById('submitProductForm').addEventListener('submit', validateForm);
    </script>

        

