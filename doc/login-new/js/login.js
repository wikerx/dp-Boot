layer.ready(function(){
    $('.code').createCode({
        len:4
    });
});

// form validate   关闭required 即可启用layer的样式
function validate(){
    if($('#username').val()==""){
        layer.msg("用户名不能为空！");
        $('#username').focus();
        return false;
    }else if($('#password').val()==""){
        layer.msg("密码不能为空！");
        $('#password').focus();
        return false;
    }else if($('#code').val()==""){
        layer.msg("验证码不能为空！");
        $('#code').focus();
        return false;
    }else if($('#code').val().toLowerCase() != $('.code').children('input').val().toLowerCase()){
        layer.msg("验证码输入错误！");
        $('#code').focus();
        return false;
    }else{
        return true;
    }
}

//tis
function tis(){
    layer.open({
        type: 2,
        title: 'Author-Mr.薛',
        maxmin: true,
        area: ['330px', '370px'],
        content: 'author.html',
        end: function(){
            layer.tips('Hi', '#about', {tips: 1})
        }
    });
}







