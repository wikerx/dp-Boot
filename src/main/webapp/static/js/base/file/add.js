/**
 * 新增-InnoDB free: 23552 kBjs
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		sysFile: {
			fileId: 0
		}
	},
	methods : {
		acceptClick: function() {
			if (!$('#form').Validform()) {
		        return false;
		    }
		    $.SaveForm({
		    	url: '../../sys/file/save?_' + $.now(),
		    	param: vm.sysFile,
		    	success: function(data) {
		    		$.currentIframe().vm.load();
		    	}
		    });
		}
	}
})
