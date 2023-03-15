import LoadedImage from "./LoadedImage";

const PartnerLogo = (props) => {
  return (
    <li
      style={{ height: "17rem", width: "25rem" }}
      key={props.imgName}
      title={props.title}
    >
      <a href={props.link} target={"_blank"} rel={"noreferrer"}>
        <LoadedImage
          imgFolder={"aboutus"}
          imgName={props.imgName}
          imgAlt={props.imgAlt}
          imgLazyLoad={true}
        />
      </a>
    </li>
  );
};

export default PartnerLogo;
