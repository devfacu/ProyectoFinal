$(document).ready(function () {

    $(".task-chk").click(function () {
        let taskId = $(this).attr("id");

        $.ajax({
            url: "/internal/task/" + taskId,
            contentType: "application/json",
            type: "POST",
            success: function () {
                const $task = $("#" + taskId);
                let taskHtml = $("#" + taskId).html();
                
                if(taskHtml === `<span class="material-icons">radio_button_unchecked</span>`) {
                    $task.html(`<span class="material-icons">check_circle_outline</span>`);
                } else {
                    $task.html(`<span class="material-icons">radio_button_unchecked</span>`);
                }
            }
        });
    });

    
    let startMinutes = 25;
    let remainingSeconds = startMinutes * 60;

    var interval = null;

    $("#minutes").text(startMinutes.toString().padStart(2, "0"));
    $("#seconds").text("00");


    function start() {
        if (remainingSeconds === 0) return;

        interval = setInterval(() => {
            remainingSeconds --;
            updateTimer();

            if (remainingSeconds === 0) {
                $("#done").text("Session Completed!! Take a Break");
                
            }
        }, 1000);

        updateInterfaceControls();
    }

    function pause() {
        clearInterval(interval);

        interval = null;

        updateInterfaceControls();
    }

    function updateTimer() {
        const minutes = Math.floor(remainingSeconds / 60);
        const seconds = remainingSeconds % 60;

        $("#minutes").text(minutes.toString().padStart(2, "0"));
        $("#seconds").text(seconds.toString().padStart(2, "0"));
    }

    const $btnStart = $("#btn-start");

    $("#btn-start").click(function () {
        if (interval === null) {
            start();
        } else {
            pause();
        }
        
    });

    function updateInterfaceControls() {    
        if (interval === null) {
            $btnStart.html(`<span class="material-icons">play_arrow</span>`)
                .addClass("timer_btn-start")
                .removeClass("timer_btn-pause");
        } else {
            $btnStart.html(`<span class="material-icons">pause</span>`)
                .addClass("timer_btn-pause")
                .removeClass("timer_btn-start");
        }
    }


    let checked = false;
    const $btnChk = $("#btn-chk");
    $btnChk.click(function () {
        if (checked) {
            $btnChk.html(`<span class="material-icons md-48">radio_button_unchecked</span>`);
            checked = false;
        } else {
            $btnChk.html(`<span class="material-icons md-48">check_circle_outline</span>`);
            checked = true;
        }
    });

    
    
});
