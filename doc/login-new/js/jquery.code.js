/**
 * Created by duanhailin on 2018/06/21.
 */
;(function ($) {
  function createCode(dom, config) {
    var _this = this;
    _this.config = config
    _this.codeList = [1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z'];
    _this.color = ['#e3730f', '#2ae3e0', '#c317e3', '#0e935f','#e30d65', '#3c8b36', '#74675a', '#654321','#123345', '#135784', '#169b9a', '#02468a'];
    _this.bgColorList = ['#d0d9f9'];
    _this.bgColor = '';
    _this.code = '';
    if (_this.config) {
      _this.codeLen = _this.config.len || 4;
    } else {
      _this.codeLen = 4;
    }
    var html = '';
    var deg = 0;
    for (i = 0; i < _this.codeLen; i++) {
      var index = Math.floor(Math.random() * _this.codeList.length);
      var idx = Math.floor(Math.random() * _this.color.length);
      var bgIdx = Math.floor(Math.random() * _this.bgColorList.length);
      if (index % 2 == 0) {
        deg = index + Math.floor(Math.random() * idx)
      } else {
        deg = -(index + Math.floor(Math.random() * idx))
      }
      html += '<span style="padding:0 6px;color:' + _this.color[idx] + ';display:inline-block;transform:rotate(' + deg + 'deg);font-size:28px;font-weight:border;">' + _this.codeList[index] + '</span>';
      _this.code += _this.codeList[index];
      _this.bgColor = _this.bgColorList[bgIdx]

    }
    html += '<input placeholder="" value="'+_this.code+'" type="hidden">';
    $(dom).css({
      width: _this.codeLen * 41 + 'px',
      padding: '1px',
      textAlign: 'center',
      display: 'inline-block',
      backgroundColor: _this.bgColor,
      cursor: "pointer",
      opacity: .4
    });
    $(dom).empty().append(html);
  }

  $.fn.createCode = function (cfg) {
    var _this = this;
    createCode(_this, cfg);
    $(_this).click(function () {
      createCode(_this, cfg);
    });
  }
})(jQuery);