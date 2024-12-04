// JavaScript для динамического отображения поля кода
function handleRoleChange() {
    var role = document.getElementById("role").value;
    var codeField = document.getElementById("teacherCodeField");

    // Если выбрана роль "Преподаватель", показываем поле ввода кода
    if (role === "Преподаватель") {
        codeField.style.display = "block";
    } else {
        codeField.style.display = "none";
    }
}
