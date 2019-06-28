/**
 * 新增-InnoDB free: 206848 kBjs
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		docNotice: {
			id: 0
		}
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../doc/notice/save?_' + $.now(),
		    	param: vm.docNotice,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
