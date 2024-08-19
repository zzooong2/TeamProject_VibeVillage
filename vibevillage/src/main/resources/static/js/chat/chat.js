function startChat() {
    const boardId = /*[[${usedBoard.usedBoardId}]]*/ 'defaultBoardId'; // 게시물 ID
    const boardOwnerId = /*[[${usedBoard.userNo}]]*/ 'defaultUserId';  // 게시물 작성자 ID
    const currentUserId = 10;
    fetch('/chat/start', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            boardId: boardId,
            boardOwnerId: boardOwnerId,
            currentUserId: currentUserId
        })
    })
        .then(response => response.json())
        .then(data => {
            if (data.roomId) {
                window.location.href = `/chat/room/${data.roomId}`;
            } else {
                alert('채팅방 생성에 실패했습니다.');
            }
        })
        .catch(error => console.error('Error:', error));
}