function convertToBase64() {
    var fileInput = document.getElementById('imageInput');
    var file = fileInput.files[0];
    var reader = new FileReader();

    reader.onloadend = function() {
        document.getElementById('base64Output').value = reader.result;
    }

    if (file) {
        reader.readAsDataURL(file);
    } else {
        document.getElementById('base64Output').value = "";
    }
}
