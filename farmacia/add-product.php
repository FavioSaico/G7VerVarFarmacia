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

        function errorInput(input, elementErrorMessage, message){
            elementErrorMessage.textContent = message;
            elementErrorMessage.style.display="block";
            input.style.borderColor="#f44336";
        }

        function validInput(input, elementErrorMessage){
            elementErrorMessage.textContent = "Completa este campo";
            elementErrorMessage.style.display="none";
            input.style.borderColor="#e7e7e7"; // #28a745 #e7e7e7
        }

        document.getElementById('MedicineImage').addEventListener('change', function() {
            const file = this.files[0];
            const maxSizeInBytes = 5 * 1024 * 1024; // 5 MB
            const allowedExtensions = /(\.jpg|\.jpeg|\.png)$/i;  // Extensiones permitidas: jpg, jpeg, png

            const missingImageFile = document.getElementById('missing-image');

            if (!allowedExtensions.test(file.name)) {
                errorInput(this,missingImageFile,'Solo se permiten archivos con extensiones .jpg, .jpeg o .png.');
                //alert('Solo se permiten archivos con extensiones .jpg, .jpeg o .png.');
                this.value = ''; // Limpiar el campo de carga para que el usuario pueda elegir otro archivo
                return;
            }

            if (file.size > maxSizeInBytes) {
                errorInput(this,missingImageFile,'El tamaño de la imagen es demasiado grande. Por favor, elige una imagen más pequeña.');
                //alert('El tamaño de la imagen es demasiado grande. Por favor, elige una imagen más pequeña.');
                this.value = ''; // Limpiar el campo de carga para que el usuario pueda elegir otro archivo
                return;
            }

            validInput(this,missingImageFile)

        });

        function validateForm(event) {
            const allowedCharacters = /^[A-Za-z0-9\s]+$/;
            const minLength = 3;
            const maxLength = 50;

            const imageFile = document.getElementById('MedicineImage');
            const missingImageFile = document.getElementById('missing-image');
            const productName = document.getElementById('productName');
            const missingName = document.getElementById('missing-name');
            const quantity = document.getElementById('quantity');
            const missingQuantity = document.getElementById('missing-quantity');
            const unitQuantity = document.getElementById('rate');
            const missingUnitQuantity = document.getElementById('missing-rate');
            const price = document.getElementById('price');
            const missingPrice = document.getElementById('missing-price');
            const prm = document.getElementById('mrp');
            const missingPrm = document.getElementById('missing-mrp');
            const batchNo = document.getElementById('Batch No');
            const missingBno = document.getElementById('missing-bno');
            const expdate = document.getElementById('expdate'); // '2023-11-02' o ''
            const missingExpdate = document.getElementById('missing-expdate')
            const brandName = document.getElementById('brandName');
            const missingBrand = document.getElementById('missing-brandName');
            const categoryName = document.getElementById('categoryName'); // '1'
            const missingCategory = document.getElementById('missing-categoryName');
            const productStatus = document.getElementById('productStatus');
            const missingProductStatus = document.getElementById('missing-productStatus');
            let errors = 0;

            if(imageFile.files.length == 0){ // vacio
                errorInput(imageFile,missingImageFile,'Selecciona una imagen.');
                errors++;
            }else{
                validInput(imageFile,missingImageFile);
            }

            // Nombre del producto
            if(productName.value.length == 0){ // vacio
                errorInput(productName,missingName,'Completa este campo');
                errors++;
            }else{ // validamos campo
                if (productName.value.length < minLength) {
                    errorInput(productName,missingName,'El nombre de la medicina debe tener al menos ' + minLength + ' caracteres.');
                    errors++;
                } else if (productName.value.length > maxLength) {
                    errorInput(productName,missingName,'El nombre de la medicina no puede tener más de ' + maxLength + ' caracteres.');
                    errors++;
                }else if (!allowedCharacters.test(productName.value)) {
                    errorInput(productName,missingName,'El nombre de la medicina solo puede contener letras, números y espacios.');
                    errors++;
                }else{ // campo valido
                    validInput(productName,missingName);
                }
            }

            // Cantidad
            if (quantity.value ==''){
                errorInput(quantity,missingQuantity,'Completa este campo');
                errors++;
            }else{
                if(quantity.value < 1){
                    errorInput(quantity,missingQuantity,'El valor debe ser superior o igual a 1');
                    errors++;
                }else if(quantity.value > 999){
                    errorInput(quantity,missingQuantity,'El valor debe ser inferior o igual a 999');
                    errors++;
                }else{
                    validInput(quantity,missingQuantity);
                }
            }

            // unitQuantity
            if(unitQuantity.value ==''){
                errorInput(unitQuantity,missingUnitQuantity,'Completa este campo');
                errors++;
            }else{
                if(unitQuantity.value < 1){
                    errorInput(unitQuantity,missingUnitQuantity,'El valor debe ser superior o igual a 1');
                    errors++;
                }else if(unitQuantity.value  > 999){
                    errorInput(unitQuantity,missingUnitQuantity,'El valor debe ser inferior o igual a 999');
                    errors++;
                }else{
                    validInput(unitQuantity,missingUnitQuantity);
                }
            }
            
            // price
            if(price.value ==''){
                errorInput(price,missingPrice,'Completa este campo');
                errors++;
            }else{
                if(price.value < 1){
                    errorInput(price,missingPrice,'El valor debe ser superior o igual a 1');
                    errors++;
                }else if(price.value  > 999){
                    errorInput(price,missingPrice,'El valor debe ser inferior o igual a 999');
                    errors++;
                }else{
                    validInput(price,missingPrice);
                }
            }
            
            // prm
            if(prm.value ==''){
                errorInput(prm,missingPrm,'Completa este campo');
                errors++;
            }else{
                if(prm.value < 1 && prm.value !=''){
                    errorInput(prm,missingPrm,'El valor debe ser superior o igual a 1');
                    errors++;
                }else if(prm.value > 500){
                    errorInput(prm,missingPrm,'El valor debe ser inferior o igual a 500');
                    errors++;
                }else{
                    validInput(prm,missingPrm);
                }
            }

            // batchNo
            if(batchNo.value ==''){
                errorInput(batchNo,missingBno,'Completa este campo');
                errors++;
            }else{
                if(batchNo.value < 100000 && batchNo.value !=''){
                    errorInput(batchNo,missingBno,'El valor debe ser superior o igual a 100000');
                    errors++;
                }else if(batchNo.value  > 999999){
                    errorInput(batchNo,missingBno,'El valor debe ser inferior o igual a 999999');
                    errors++;
                }else{
                    validInput(batchNo,missingBno);
                }
            }

            // expdate
            if(expdate.value ==''){
                errorInput(expdate,missingExpdate,'Completa este campo');
                errors++;
            }else{
                let hoy = new Date(Date.now());
                hoy = new Date(hoy.getFullYear(),hoy.getMonth(),hoy.getDate());
                let fecha = new Date(expdate.value);
                fecha = new Date(fecha.getFullYear(),fecha.getMonth(),fecha.getDate()+1);
                
                if(fecha < hoy){ //  && fecha !=''
                    errorInput(expdate,missingExpdate,'El valor debe ser ' + hoy.toLocaleDateString()+ ' (Fecha Actual) o superior');
                    errors++;
                }else if(fecha > new Date('2030-12-31')){
                    errorInput(expdate,missingExpdate,'El valor debe ser 31/12/2030 o anterior');
                    errors++;
                }else{
                    validInput(expdate,missingExpdate);
                }
            }

            if(brandName.value == ''){
                errorInput(brandName,missingBrand,'Selecciona un elemento de la lista');
                errors++;
            }else{
                validInput(brandName,missingBrand);
            }

            if(categoryName.value == ''){
                errorInput(categoryName,missingCategory,'Selecciona un elemento de la lista');
                errors++;
            }else{
                validInput(categoryName,missingCategory);
            }

            if(productStatus.value == ''){
                errorInput(productStatus,missingProductStatus,'Selecciona un elemento de la lista');
                errors++;
            }else{
                validInput(productStatus,missingProductStatus);
            }

            if(errors > 0){
                event.preventDefault();
                return false;
            }
            
            return true;
        }

        document.getElementById('submitProductForm').addEventListener('submit', validateForm);
    </script>

        

