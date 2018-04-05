package com.mobinius.auditpdfdemo.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.mobinius.auditpdfdemo.R;
import com.mobinius.auditpdfdemo.model.AuditCategory;
import com.mobinius.auditpdfdemo.model.AuditMap;
import com.mobinius.auditpdfdemo.model.AuditModelClass;
import com.mobinius.auditpdfdemo.model.AuditParticipants;
import com.mobinius.auditpdfdemo.model.AuditQuestion;
import com.mobinius.auditpdfdemo.model.AuditSignature;
import com.mobinius.auditpdfdemo.model.TicketTimeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    private Button mRenderPdf, mViewPdf;
    Document doc = new Document();
    AuditModelClass auditModelClass = new AuditModelClass();
    List<AuditCategory> listCategorys = new ArrayList<>();
    List<AuditSignature> listSignature = new ArrayList<>();
    List<TicketTimeline> listTimeline = new ArrayList<>();
    List<AuditMap> listAuditMap = new ArrayList<>();
    List<AuditParticipants> listAuditParticipants = new ArrayList<>();
    DottedLineSeparator dottedline = new DottedLineSeparator();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //load json
        String jsonStr = loadJSONFromAsset();
        //parsing json data
        parseJson(jsonStr);
        //create pdf
        createAndDisplayPdf(auditModelClass.getName());
        createCategory__();

    }

    private void parseJson(String jsonStr) {
        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String id = jsonObject.getString("_id");
                auditModelClass.setId(id);
                String rev = jsonObject.getString("_rev");
                auditModelClass.setRev(rev);
                String archived = jsonObject.getString("archived");
                auditModelClass.setArchived(archived);
                String auditType = jsonObject.getString("auditType");
                auditModelClass.setAuditType(auditType);

                JSONObject authorObject = jsonObject.getJSONObject("author");
                ArrayList<String> authorList = new ArrayList<>();
                String email = authorObject.getString("email");
                String type1 = authorObject.getString("type");
                authorList.add(email);
                authorList.add(type1);
                auditModelClass.setAuthor(authorList);

                JSONObject datesObject = jsonObject.getJSONObject("dates");
                ArrayList<String> dateList = new ArrayList<>();
                String creationDate = datesObject.getString("creationDate");
                String lastModifiedDate = datesObject.getString("lastModifiedDate");
                String completionDate = datesObject.getString("completionDate");
                dateList.add(creationDate);
                dateList.add(lastModifiedDate);
                dateList.add(completionDate);
                auditModelClass.setDates(dateList);

                String groupId = jsonObject.getString("groupId");
                auditModelClass.setGroupId(groupId);
                JSONArray mapArray = jsonObject.getJSONArray("maps");
              /*  AuditMap auditMap = new AuditMap();
                JSONObject mapObject = mapArray.getJSONObject(0);
                String mapID = mapObject.getString("mapID");
                auditMap.setMapId(mapID);
                String mapname = mapObject.getString("mapname");
                auditMap.setMapName(mapname);
                listAuditMap.add(auditMap);
                auditModelClass.setListAuditMaps(listAuditMap);*/
                String name = jsonObject.getString("name");
                auditModelClass.setName(name);

                JSONObject participantsObject = jsonObject.getJSONObject("participants");
                AuditParticipants auditParticipant = new AuditParticipants();

                JSONArray informedArray = participantsObject.getJSONArray("informed");
                JSONObject informedObject = informedArray.getJSONObject(0);
                List<String> informed = new ArrayList<>();

                String email_ = informedObject.getString("email");
                informed.add(email_);
                String type2 = informedObject.getString("type");
                informed.add(type2);
                auditParticipant.setInformed(informed);

                String type3 = participantsObject.getString("type");
                auditParticipant.setType(type3);
                JSONObject responsibleObject = participantsObject.getJSONObject("responsible");
                String email_1 = responsibleObject.getString("email");
                String type = responsibleObject.getString("type");
                List<String> responsible = new ArrayList<>();
                responsible.add((email_1));
                responsible.add(type);
                auditParticipant.setResponsible(responsible);

                listAuditParticipants.add(auditParticipant);
                auditModelClass.setParticipants(listAuditParticipants);

                String project = jsonObject.getString("project");
                auditModelClass.setProject(project);


                JSONArray questionsArray = jsonObject.getJSONArray("questions");
                for (int i = 0; i < questionsArray.length(); i++) {
                    AuditCategory auditCategory = new AuditCategory();

                    JSONObject categoryObject = questionsArray.getJSONObject(i);
                    String categoryName = categoryObject.getString("categoryName");
                    auditCategory.setCategoryName(categoryName);

                    JSONArray questionArray = categoryObject.getJSONArray("questions");
                    for (int j = 0; j < questionArray.length(); j++) {
                        AuditQuestion auditQuestion = new AuditQuestion();
                        JSONObject questionArrayObject = questionArray.getJSONObject(j);
                        auditQuestion.setQuestionName(questionArrayObject.getString("question"));
                        JSONArray questionArray_ = questionArrayObject.getJSONArray("answer");

                        ArrayList<String> listdata = new ArrayList<String>();
                        if (questionArray_ != null) {
                            for (int k = 0; k < questionArray_.length(); k++) {
                                listdata.add(questionArray_.getString(k));
                                System.out.print(listdata);
                            }
                        }

                        auditQuestion.setListAnswer(listdata);

                        JSONArray ticketArray_ = questionArrayObject.getJSONArray("ticket");

                        List<String> listdata_ = new ArrayList<String>();
                        if (ticketArray_ != null) {
                            for (int k = 0; k < ticketArray_.length(); k++) {
                                listdata_.add(ticketArray_.getString(k));
                            }
                        }

                        auditQuestion.setListTicket(listdata_);

                        String description = questionArrayObject.getString("description");
                        auditQuestion.setDescription(description);
                        JSONObject settingsObject = questionArrayObject.getJSONObject("settings");
                        String answertype = settingsObject.getString("answertype");
                        if (answertype.equals("multiplechoice")) {

                            JSONArray answerArray = settingsObject.getJSONArray("answer");
                            String choice = settingsObject.getString("choice");
                        }

                        JSONObject settingsObject_ = categoryObject.getJSONObject("settings");
                        String duplicate = settingsObject_.getString("duplicate");
                        //auditQuestionList.add(auditQuestion);

                        auditCategory.getListQuestions().add(auditQuestion);
                    }

                   // auditCategory.setListQuestions(auditQuestionList);
//                    listCategorys.add(auditCategory);
                    listCategorys.add(auditCategory);
                }

                auditModelClass.setListCategorys(listCategorys);

                JSONArray signatureArray = jsonObject.getJSONArray("signature");
                AuditSignature auditSignature = new AuditSignature();
                JSONObject signatureObject = signatureArray.getJSONObject(0);
                String image = signatureObject.getString("image");
                auditSignature.setSignatureImageName(image);
                String name_ = signatureObject.getString("name");
                auditSignature.setSignatureName(name_);
                listSignature.add(auditSignature);
                auditModelClass.setListSignatures(listSignature);

                String status = jsonObject.getString("status");
                auditModelClass.setStatus(status);
                String template = jsonObject.getString("template");
                auditModelClass.setTemplate(template);

                JSONArray timelineArray = jsonObject.getJSONArray("timeline");
                TicketTimeline timeline = new TicketTimeline();
                JSONObject timelineObject = timelineArray.getJSONObject(0);
                JSONObject actorObject = timelineObject.getJSONObject("actor");
                ArrayList<String> actor = new ArrayList<>();
                String email1 = actorObject.getString("email");
                actor.add(email1);
                String interface_ = actorObject.getString("interface");
                actor.add(interface_);
                String interfaceVersion = actorObject.getString("interfaceVersion");
                actor.add(interfaceVersion);
                String type5 = actorObject.getString("type");
                actor.add(type5);
                timeline.setActor(actor);
                String operation = timelineObject.getString("operation");
                timeline.setOpearation(operation);
                JSONObject personObject = timelineObject.getJSONObject("person");
                ArrayList<String> person = new ArrayList<>();
                String email2 = personObject.getString("email");
                person.add(email2);
                String type6 = personObject.getString("type");
                person.add(type6);
                timeline.setPerson(person);
                String role = timelineObject.getString("role");
                timeline.setRole(role);
                String time = timelineObject.getString("time");
                timeline.setTime(time);
                String type7 = timelineObject.getString("type");
                timeline.setType(type7);
                listTimeline.add(timeline);
                auditModelClass.setListAuditTimelines(listTimeline);
                String type4 = jsonObject.getString("type");
                auditModelClass.setType(type4);
                JSONObject _attachmentsObject = jsonObject.getJSONObject("_attachments");
                JSONObject sign_jpeg_object = _attachmentsObject.getJSONObject("signature-20171123T104007754.jpeg");
                String content_type = sign_jpeg_object.getString("content_type");
                String revpos = sign_jpeg_object.getString("revpos");
                String digest = sign_jpeg_object.getString("digest");
                String length = sign_jpeg_object.getString("length");
                String stub = sign_jpeg_object.getString("stub");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        mRenderPdf = (Button) findViewById(R.id.render_pdf_text_view);
        mViewPdf = (Button) findViewById(R.id.view_pdf_text_view);
        mRenderPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        mViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPdf("audit.pdf", "PDF");

            }
        });

    }

    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(MainActivity.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("audits.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void createAndDisplayPdf(String text) {

        LineSeparator ls = new LineSeparator();
        dottedline.setOffset(-2);
        dottedline.setGap(2f);
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";

            File dir = new File(path);
            if (!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "audit.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter writer = PdfWriter.getInstance(doc, fOut);
            //open the document
            doc.open();

            Paragraph p = new Paragraph(text);
            p.setAlignment(Paragraph.ALIGN_CENTER);
            Font bfBold12 = new Font(Font.FontFamily.HELVETICA,
                    12, Font.BOLD, new BaseColor(0, 0, 0));
            p.setFont(bfBold12);
            doc.add(p);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.images_);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            Image myImg = Image.getInstance(stream.toByteArray());
            myImg.setAlignment(Image.MIDDLE);
            //add image to document
            doc.add(myImg);

            Paragraph p1 = new Paragraph("Audit ID :" + auditModelClass.getId());
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p1);

            Paragraph p2 = new Paragraph("Audit State :" + auditModelClass.getStatus());
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p2);


            ArrayList<String> dateList = auditModelClass.getDates();
            Paragraph p3 = new Paragraph("Made on : " + dateList.get(0));
            p3.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph p4 = new Paragraph("Last modified on : " + dateList.get(1));
            p4.setAlignment(Paragraph.ALIGN_CENTER);

            doc.add(p3);
            doc.add(p4);
            doc.add(new Chunk(ls));

            ArrayList<String> authorList = auditModelClass.getAuthor();
            Paragraph p_ = new Paragraph("Author : " + authorList.get(0));
            p_.setAlignment(Paragraph.ALIGN_CENTER);
            doc.add(p_);
            doc.add(new Chunk(ls));

            doc.newPage();

//            doc.add(Chunk.NEWLINE);//adding blank line
//            doc.add(new Paragraph(" "));//adding blank linedoc.add( Chunk.NEWLINE );//adding blank line

            String name = auditModelClass.getName();
            Paragraph p6 = new Paragraph(name);
            p6.setAlignment(Paragraph.ALIGN_LEFT);
            doc.add(p6);
            doc.add(new Chunk(ls));

            for(int i=0;i<auditModelClass.getListCategorys().size();i++){

                createCategory(i);
                for (int j=0;j<auditModelClass.getListCategorys().get(i).getListQuestions().size(); j++){
                    createQuestion(i,j);

                }
                doc.add(new Chunk(ls));

            }

/*
           createMaps();
*/
            createParticipants();
            doc.add(new Chunk(ls));
            createNameSignature();
            doc.add(new Chunk(ls));
            createTimeline();
            doc.add(new Chunk(ls));

           /* Paragraph p0_ = new Paragraph("This line will be underlined with a dotted line.");
            CustomDashedLineSeparator dottedline_ = new CustomDashedLineSeparator();
            dottedline_.setOffset(-2);
            dottedline_.setGap(2f);
            p0_.add(dottedline_);
            doc.add(p0_);*/
            /*add text left and right*/

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

    }

   /* private void createMaps() {
        String mapID = auditModelClass.getListAuditMaps().get(0).getMapId();
        String mapName = auditModelClass.getListAuditMaps().get(0).getMapName();
        Paragraph p = new Paragraph("Map :");
        Paragraph p1 = new Paragraph("Map ID :" + mapID);
        Paragraph p2 = new Paragraph("Map Name :" + mapName);
        try {
            p.add(dottedline);
            doc.add(p);
            doc.add(p1);
            doc.add(p2);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }*/


    private void createParticipants() {
        String informed = auditModelClass.getParticipants().get(0).getInformed().get(0);
        String responsible = auditModelClass.getParticipants().get(0).getResponsible().get(0);
        Paragraph p = new Paragraph("Informed");
        Paragraph p1 = new Paragraph("Responsible");
        p.add(dottedline);
        p1.add(dottedline);
        Paragraph p7 = new Paragraph(informed);
        Paragraph p8 = new Paragraph(responsible);
        try {
            doc.add(p);
            doc.add(p7);
            doc.add(Chunk.NEWLINE);//adding blank line
            doc.add(p1);
            doc.add(p8);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private void createTimeline() {
        String time = auditModelClass.getListAuditTimelines().get(0).getTime();
        String operation = auditModelClass.getListAuditTimelines().get(0).getOpearation();
        String role = auditModelClass.getListAuditTimelines().get(0).getRole();
        String actor = auditModelClass.getListAuditTimelines().get(0).getActor().get(0);
        String person = auditModelClass.getListAuditTimelines().get(0).getPerson().get(0);
        Paragraph p = new Paragraph("Timeline");
        Paragraph p7 = new Paragraph(time);
        Paragraph p8 = new Paragraph(actor + " " + operation + "ed  " + person + " " + "as " + role);

        try {
            p.add(dottedline);
            doc.add(p);
            doc.add(p7);
            doc.add(p8);
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }

    private void createNameSignature() {
        String signName = auditModelClass.getListSignatures().get(0).getSignatureName();
        String signImageName = auditModelClass.getListSignatures().get(0).getSignatureImageName();
        Paragraph p = new Paragraph("Name and Signatuure");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.ic_check_select);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        Image myImg = null;
        try {
            myImg = Image.getInstance(stream.toByteArray());
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myImg.setAlignment(Image.RIGHT);


        try {
            p.add(dottedline);
            doc.add(p);
            Chunk glue = new Chunk(new VerticalPositionMark());
            Paragraph p7 = new Paragraph(signName);
            p7.add(new Chunk(glue));
            p7.add(signImageName);
            doc.add(p7);
            doc.add(myImg);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }


    public void createCategory(int a) {

        String categoryName = auditModelClass.getListCategorys().get(a).getCategoryName();
        Paragraph p7 = new Paragraph(categoryName);
        p7.setAlignment(Paragraph.ALIGN_LEFT);
        try {
            p7.add(dottedline);

            doc.add(p7);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    public void createQuestion(int a,int b) {
        String questionName = auditModelClass.getListCategorys().get(a).getListQuestions().get(b).getQuestionName();

        ArrayList<String> questionArray_ = auditModelClass.getListCategorys().get(a).getListQuestions().get(b).getListAnswer();
        List<String> ticketArray_ = auditModelClass.getListCategorys().get(a).getListQuestions().get(b).getListTicket();
        String description = auditModelClass.getListCategorys().get(a).getListQuestions().get(b).getDescription().toString();

        String questionList = Arrays.toString(questionArray_.toArray()).replace("[", "").replace("]", "");
        String ticketList = Arrays.toString(ticketArray_.toArray()).replace("[", "").replace("]", "");

        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph p7 = new Paragraph(questionName);
        p7.add(new Chunk(glue));
        p7.add(questionList);
        Paragraph p8 = new Paragraph(description);
        Paragraph p9 = new Paragraph(ticketList);

        try {
            doc.add(p7);
            doc.add(p8);
            doc.add(p9);
            doc.add(Chunk.NEWLINE);//adding blank line

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    private class CustomDashedLineSeparator extends DottedLineSeparator {
        protected float dash = 5;
        protected float phase = 2.5f;

        public float getDash() {
            return dash;
        }

        public float getPhase() {
            return phase;
        }

        public void setDash(float dash) {
            this.dash = dash;
        }

        public void setPhase(float phase) {
            this.phase = phase;
        }

        public void draw(PdfContentByte canvas,
                         float llx, float lly, float urx, float ury, float y) {
            canvas.saveState();
            canvas.setLineWidth(lineWidth);
            canvas.setLineDash(dash, gap, phase);
            drawLine(canvas, llx, urx, y);
            canvas.restoreState();
        }
    }

    public void createCategory__() {
        for (AuditCategory auditCategory : auditModelClass.getListCategorys()) {
            System.out.println("createCategory__ CategoryName " + auditCategory.getCategoryName());
            for (AuditQuestion auditQuestion : auditCategory.getListQuestions()) {
                System.out.println("createCategory__ QuestionName" + auditQuestion.getQuestionName());

            }
        }

    }
}