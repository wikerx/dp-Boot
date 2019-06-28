/**
 * 编辑-InnoDB free: 206848 kBjs
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		docNotice: {
			id: 0
		}
	},
	methods : {
		setForm: function() {
			$.SetForm({
				url: '../../doc/notice/info?_' + $.now(),
		    	param: vm.docNotice.id,
		    	success: function(data) {
		    		vm.docNotice = data;
		    	}
			});
		},
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.ConfirmForm({
		    	url: '../../doc/notice/update?_' + $.now(),
		    	param: vm.docNotice,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})