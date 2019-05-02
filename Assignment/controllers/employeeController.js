const express = require('express');
var router=express.Router();
const mongoose = require('mongoose');
const Employee=mongoose.model('Employee');
router.get('/',(req,res)=>{
    res.render('employee/index')
});


router.get('/home',(req,res)=>{
    res.render('employee/home')
});


router.get('/employee',(req,res) => {
            res.render("employee/addOrEdit",{
                viewTitle:"Insert Customer"
            });
    });




router.post('/employee',(req,res)=>{
    if(req.body._id == '')
        insertRecord(req,res);
        else 
        updateRecord(req,res);
});



function insertRecord(req,res){
        var employee = new Employee();
        employee.fullName=req.body.fullName;
        employee.email=req.body.email;
        employee.mobile=req.body.mobile;
        employee.city=req.body.city;
        employee.save((err,doc) => {
            if(!err)
            res.redirect('/list');
            else{
                if(err.name == 'ValidationError'){
                    handleValidationError(err, req.body);
                    res.render("employee/addOrEdit",{
                        viewTitle:"Insert Customer",
                        employee : req.body
                    });
                }
                else
                console.log('Error during record insertin:'+err);
            }
        });

}
function updateRecord(req,res){
        Employee.findOneAndUpdate( {_id:req.body._id}, req.body, {new:true},(err, doc) => {
            if( !err) { res.redirect('/list');}
            else{
                if(err.name == 'ValidationError'){


                    handleValidationError(err,req.body);
                    res.render("employee/addOrEdit",{
                        viewTitle:'Update Customer',
                        employee: req.body
                    });
                }
                else 
                console.log("Error during record update: "+ err);

            }

        });
}
router.get('/list',(req,res) => {
Employee.find((err,docs)=>{
    if(!err){
        res.render("employee/list",{
            list : docs
        });
     
    }
    else{
        console.log('Error in retrieving employee list:'+err);
    }

});
});
function handleValidationError(err,body){
    for(field in err.errors){
        switch (err.errors[field].path){
            case 'fullName':
            body['fullNameError'] = err.errors[field].message;
            break;
            case 'email':
            body['emailError'] = err.errors[field].message;
            break;
            default:
            break;
        }
    }
}



router.get('/json',(req,res) => {
    Employee.find((err,docs) => {
        if(!err){
            res.writeHead(200,{"Content-Type":"application/json"});
            res.end(JSON.stringify(docs));
        }
        else{
            console.log('Error in retrieving data'+err)
        }
    })
});

router.post('/api/add',(req,res,docs)=>{
    // res.send(200,{"_id":"abc"});
    // res.end(JSON.stringify(docs))
    var employee = new Employee();
    employee.fullName=req.body.fullName;
        employee.email=req.body.email;
        employee.mobile=req.body.mobile;
        employee.city=req.body.city;
    employee.save((docs) => {
        res.send(200,{"_id":req.body.id});
        res.end(JSON.stringify(docs));
    });
});
router.put('/api/update/:id',(req,res) => {
    Employee.findOneAndUpdate( {_id:req.body._id}, req.body, {new:true},(err, docs) => {
            if(!err){
                res.send(200,{"_id":req.params.id});
                res.end(JSON.stringify(docs));
            }
    });
});
router.delete('/api/delete/:id',(req,res) => {
    Employee.findByIdAndRemove(req.params.id,(err,docs) => {
            if(!err){
                res.send(200,{"_id":req.params.id});
                res.end(JSON.stringify(docs));
            }
    });
});



router.get('/:id',(req,res)=>{
    Employee.findById(req.params.id, (err,doc) => {
        if(!err){
            res.render("employee/addOrEdit",{
                viewTitle: "Update Customer",
                employee: doc
            });
        }
    });


});


router.get('/delete/:id',(req,res) => {
    Employee.findByIdAndRemove(req.params.id, (err,doc) => {
        if(!err){
            res.redirect('/list');
        }
        else{console.log("Error in employee delete: "+err);}
    });
});

module.exports=router;