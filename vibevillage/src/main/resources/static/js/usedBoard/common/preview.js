document.addEventListener('DOMContentLoaded', function() {
    const mainFileInput = document.getElementById('main-file-input');
    const previewFileInput = document.getElementById('preview-file-input');
    const mainImgContainer = document.getElementById('mainImg');
    const previewContainer = document.getElementById('preview-container');
    const submitButton = document.getElementById('submit-button');

    let mainImage = null;
    let previewImages = [];

    mainFileInput.addEventListener('change', function() {
        const file = mainFileInput.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                mainImage = file;
                addMainImage(e.target.result);
            };
            reader.readAsDataURL(file);
        }
    });

    previewFileInput.addEventListener('change', function() {
        const files = Array.from(previewFileInput.files);

        files.forEach((file, index) => {
            const reader = new FileReader();
            reader.onload = function(e) {
                previewImages.push(file);
                addPreviewImage(e.target.result, previewImages.length - 1);
            };
            reader.readAsDataURL(file);
        });
    });

    function addMainImage(imageSrc) {
        mainImgContainer.innerHTML = '';
        const imgWrapper = document.createElement('div');
        const img = document.createElement('img');
        img.classList.add('gallery');
        img.src = imageSrc;
        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete');
        deleteButton.textContent = 'Delete';
        deleteButton.addEventListener('click', function() {
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
        previewItem.classList.add('preview-item');
        const img = document.createElement('img');
        img.classList.add('gallery');
        img.src = imageSrc;
        const deleteButton = document.createElement('button');
        deleteButton.classList.add('delete');
        deleteButton.textContent = 'Delete';
        deleteButton.addEventListener('click', function() {
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

    submitButton.addEventListener('click', function() {
        const formData = new FormData();
        if (mainImage) {
            formData.append('mainFile', mainImage);
        }
        previewImages.forEach(image => {
            formData.append('previewFiles', image);
        });

        formData.append('usedBoardTitle', document.getElementById('usedBoardTitle').value);
        formData.append('usedBoardProductName', document.getElementById('usedBoardProductName').value);
        formData.append('usedBoardProductPrice', document.getElementById('usedBoardProductPrice').value);
        formData.append('usedBoardContent', document.getElementById('usedBoardContent').value);
        formData.append('usedBoardLocation', document.getElementById('sample5_address').value);
        formData.append('categoryId', document.getElementById('categoryId').value);


        fetch('/used/boardEnroll', {
            method: 'POST',
            body: formData,
        })
            .then(response => response.json())  // 응답을 JSON 형식으로 변환
            .then(data => {
                console.log(data);
                alert(data.message);  // JSON 응답의 메시지 출력
                if (data.message === "Upload successful!") {
                    window.location.href = "/used/boardList";  // 성공 시 리디렉션
                }
            })
            .catch(error => {
                console.error(error);
                alert('Upload failed!');
            });
    });
});
