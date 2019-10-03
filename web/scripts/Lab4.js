var Lab4 = ( function() {
    
    return {
        
        convert: function(rates) {
            
            /*
             * This function accepts the data object sent by the server
             * ("rates") as an argument.  It should: get the amount (in USD)
             * entered by the user in the "input" form field, iterate through
             * the currency exchange rates given in the data object, multiply
             * each rate by the given number of U.S. Dollars, and compute the
             * corresponding amount for each currency.  These amounts should be
             * shown in the "output" element of the page, along with the
             * currency codes, separated by colons and formatted to two decimal
             * places.  (See the screenshot given with this assignment.)
             */
            
            var lastLine = ""
            var input = $('#input').val();
            var line;
            
            for(line in rates.Rates){
                // variable to build html line by concatenation
                var htmlLine = "";
                
                // separate variable for user input for data integrity purposes
                var origInput = input;
                
                // multiplies user input by the value of the current rate
                var converted = origInput * rates.Rates[line];
                                
                // loads rate name and converted input into html paragraph tag at the end of output element
                htmlLine = htmlLine.concat("<p>",line," : ",converted,"</p>");
                $('#output').append(htmlLine);
            }
            lastLine = lastLine.concat("<p> Based on ",rates.Date," Exchange rates </p>");
            $('#output').append(lastLine);

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