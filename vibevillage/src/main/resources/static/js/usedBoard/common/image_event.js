    const mainFileInput = document.getElementById('main-file-input');
    const previewFileInput = document.getElementById('preview-file-input');
    const mainImgContainer = document.getElementById('mainImg');
    const previewContainer = document.getElementById('preview-container');

    let mainImage = null;
    let previewImages = [];

    mainFileInput.addEventListener('change', function () {
        const file = mainFileInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                mainImage = file;
                addMainImage(e.target.result);
            };
            reader.readAsDataURL(file);
        }
    });

    previewFileInput.addEventListener('change', function () {
        const files = Array.from(previewFileInput.files);

        files.forEach((file, index) => {
            const reader = new FileReader();
            reader.onload = function (e) {
                previewImages.push(file);
                addPreviewImage(e.target.result, previewImages.length - 1);
            };
            reader.readAsDataURL(file);
        });
    });

    function addMainImage(imageSrc) {
        mainImgContainer.innerHTML = '';
        const imgWrapper = document.createElement('div');
        imgWrapper.classList.add('img-wrapper');
        const img = document.createElement('img');
        img.classList.add('gallery');
        img.src = imageSrc;
        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete');
        deleteButton.textContent = 'X';
        deleteButton.addEventListener('click', function () {
            imgWrapper.remove();
            mainImage = null;
            mainFileInput.value = ''; // Reset the file input
        });
        imgWrapper.appendChild(img);
        imgWrapper.appendChild(deleteButton);
        mainImgContainer.appendChild(imgWrapper);
    }

    function addPreviewImage(imageSrc, index) {
        const previewItem = document.createElement('li');
        previewItem.classList.add('preview-item', 'img-wrapper');
        const img = document.createElement('img');
        img.classList.add('gallery');
        img.src = imageSrc;
        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete');
        deleteButton.textContent = 'X';
        deleteButton.addEventListener('click', function () {
            previewItem.remove();
            previewImages.splice(index, 1);
            updatePreviewInput(); // Update the file input to reflect the current state
        });
        previewItem.appendChild(img);
        previewItem.appendChild(deleteButton);
        previewContainer.appendChild(previewItem);
    }

    function updatePreviewInput() {
        const dataTransfer = new DataTransfer();
        previewImages.forEach(file => dataTransfer.items.add(file));
        previewFileInput.files = dataTransfer.files;
    }

    document.addEventListener('DOMContentLoaded', function() {
        const mainFileInput = document.getElementById('main-file-input');
        const previewFileInput = document.getElementById('preview-file-input');
        const mainImgContainer = document.getElementById('mainImg');
        const previewContainer = document.getElementById('preview-container');
    
        mainFileInput.addEventListener('change', function(event) {
            handleFileUpload(event.target.files[0], 500, 500, mainImgContainer);
        });
    
        previewFileInput.addEventListener('change', function(event) {
            Array.from(event.target.files).forEach(file => {
                handleFileUpload(file, 100, 100, previewContainer);
            });
        });
    
    })