$(document).ready(function(){
    $("#file-input").on("change", function(){
        var files = $(this)[0].files;
        $("#preview-container").empty();
        if(files.length > 0){
            for(var i = 0; i < files.length; i++){
                var reader = new FileReader();
                reader.onload = function(e){
                    // 이미지와 삭제 버튼을 담는 div 생성
                    var previewItem = $("<div class='preview-item'></div>");
                    $("<img class='gallery' src='" + e.target.result + "'>").appendTo(previewItem);
                    $("<button class='delete'>Delete</button>").appendTo(previewItem);
                    $("#preview-container").append(previewItem);
                };
                reader.readAsDataURL(files[i]);
            }
        }
    });

    $("#preview-container").on("click", ".delete", function(){
        // 삭제 버튼의 부모 요소('.preview-item')를 제거
        $(this).parent('.preview-item').remove();
        // 필요 시 파일 입력 필드의 값을 비움
        $("#file-input").val("");
    });
});
