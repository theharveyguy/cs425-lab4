var Lab4 = ( function() {
    
    return {
        
        convert: function(rates) {
            
            /*
             * This function accepts the data object sent by the server
             * ("rates") as an argument.  It should: get the amount (in USD)
             * entered by the user in the "input" form field, iterate through
             * the currency exchange rates given in the data object, multiply
             * each rateby the given number of U.S. Dollars, and compute the
             * corresponding amount for each currency.  These amounts should be
             * shown in the "output" element of the page, along with the
             * currency codes, separated by colons and formatted to two decimal
             * places.  (See the screenshot given with this assignment.)
             */
            
            var input = $('#input').val();
            var output = "<p>";
            
            for(var i = 0; i<rates.length; i++){
                var line = "";
                /* TODO plan:
                 *  1. Multiply input by rate double (trim to 2 decimals?)
                 *  2. For each line in the JSON object, make a new line of html
                 *  3. Add line to the end of output
                 *  4. After loop is finished show the output
                 */
                input = input * rates[1];
                line+" "+rates[0]+" : "+input+"<p/>"+"\n"+"<p>";
                concat(output, line);
            }
                        
            $('#output').html(output + "\n Based on 2019-09-20 Exchange rates");

        },//my work is in this method -MH
        
        getConversion: function() {
            
            /*
             * This method should send an Ajax request to our API to get the
             * latest exchange rates.  Use "latest" as the URL and "json" as the
             * data type, so that the data will be automatically parsed to a
             * JavaScript object.  (In the sample code in the "HTTP Basics"
             * lecture notes, this object is called "response".  Then, invoke
             * the helper function "convert()" in the callback function to 
             * perform the conversion.  (If you are unclear about the purpose of
             * the "that" variable shown here, see Page 6 of the "Functions and
             * Objects" lecture notes.
             */
            
            var that = this;
            
            $.ajax({
                url: 'latest',
                method: 'GET',
                dataType: 'json',
                success: function(response) {
                    that.convert(response);                    
                }
            }); 
            
        },
        
        init: function() {
            
            /* Output the current version of jQuery (for diagnostic purposes) */
            
            $('#output').html( "jQuery Version: " + $().jquery );
            
        }
        
    };
    
}());