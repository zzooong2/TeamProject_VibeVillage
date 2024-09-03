function startChat() {
    fetch('/chat/start', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            boardId: boardId,
            boardOwnerId: boardOwnerId,
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