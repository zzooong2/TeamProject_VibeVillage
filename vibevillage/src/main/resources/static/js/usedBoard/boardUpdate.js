
function deleteCurrentMainImage(item) {
    const main = document.getElementById("currentMain");
    if (main) {
        deleteList.push(item);  // 삭제 리스트에 추가
        main.remove();  // 화면에서 이미지 제거
    }
}

function deleteCurrentSubImage(item) {
    const subImage = document.querySelector(`img[name="${item}"]`);
    if (subImage) {
        deleteList.push(item);  // 삭제 리스트에 추가
        // item의 인덱스를 찾은 후, 해당 인덱스의 요소를 배열에서 제거
        const index = previewImages.indexOf(item);
        if (index > -1) {  // item이 배열에 존재하는 경우
            previewImages.splice(index, 1);
        }
        subImage.closest('li').remove();  // 화면에서 이미지 제거
    }
}

document.addEventListener('DOMContentLoaded', function () {
    const submitButton = document.getElementById('submit-button');


    submitButton.addEventListener('click', function () {
        const formData = new FormData();
        if (mainImage) {
            formData.append('mainFile', mainImage);
        }
        previewImages.forEach(image => {
            formData.append('previewFiles', image);
        });
        formData.append('province', addressData.sido);
        formData.append('city', addressData.sigungu);
        formData.append('usedBoardTitle', document.getElementById('usedBoardTitle').value);
        formData.append('usedBoardProductName', document.getElementById('usedBoardProductName').value);
        formData.append('usedBoardProductPrice', document.getElementById('usedBoardProductPrice').value);
        formData.append('usedBoardContent', document.getElementById('usedBoardContent').value);
        formData.append('usedBoardLocation', document.getElementById('sample5_address').value);
        formData.append('categoryId', document.getElementById('categoryId').value);
        formData.append('usedBoardId', boardId);
        formData.append('gpsLatitude', latitude);
        formData.append('gpsLongitude', longitude);
        formData.append('deleteList', deleteList);
        fetch('/used/update', {
            method: 'POST',
            body: formData,
        })
            .then(response => response.json())  // 응답을 JSON 형식으로 변환
            .then(data => {
                console.log(data);
                alert(data.message);  // JSON 응답의 메시지 출력
                if (data.message === "Update successful!") {
                    window.location.href = "/used/boardList/1";  // 성공 시 리디렉션
                }
            })
            .catch(error => {
                console.error(error);
                alert('Update failed!');
            });
    });
});
