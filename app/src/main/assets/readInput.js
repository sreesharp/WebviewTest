javascript:{
      var data ='';
      var inputs = document.getElementsByTagName('input');
      for (var i = 0; i < inputs.length; i++) {
        var field = inputs[i];
        if (field.type != 'button')
            data += '¾' + field.name + '¼' + field.value;
            inputs[i].value = '';
       }
      JsHandler.processHTML(data);
  }
